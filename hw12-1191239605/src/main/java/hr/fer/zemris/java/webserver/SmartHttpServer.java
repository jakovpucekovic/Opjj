package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 *	Class which represents a simple http server capable of taking
 *	http GET requests that require some actions such as executing
 *	smart scripts, returning pictures, generating simple html pages etc.
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class SmartHttpServer {

	/**Ip address of the server.*/
	private String address;
	
	/**Domain name of the server.*/
	private String domainName;
	
	/**Port on which the server listens.*/
	private int port;
	
	/**Number of thread in the thread pool.*/
	private int workerThreads;

	/**Duration of user sessions in seconds.*/
	private int sessionTimeout;
	
	/**{@link Map} of all supported mimeTypes.*/
	private Map<String, String> mimeTypes = new HashMap<>();
	
	/**Server thread.*/
	private ServerThread serverThread;
	
	/**Thread pool.*/
	private ExecutorService threadPool;
	
	/**{@link Path} to the directory which is the top directory for this server.*/
	private Path documentRoot;
	
	/**{@link Map} of all paths with their respective {@link IWebWorker}s.*/
	private Map<String, IWebWorker> workersMap = new ConcurrentHashMap<>();
	
	/**{@link Map} of current sessions.*/
	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<>();

	/**Device for creation of random session IDs.*/
	private Random sessionRandom = new Random();
	
	
	/**
	 * 	Constructs a new {@link SmartHttpServer} from the given configuration file.
	 * 	The configuration file must contain all of the following: address, domainName,
	 * 	port, workerThreads, timeout, documentRoot, mimeConfig 
	 * 	@param configFileName Path to the configuration file for the server.
	 */
	public SmartHttpServer(String configFileName) {
		try (InputStream input = Files.newInputStream(Paths.get(configFileName))) {
            Properties prop = new Properties();
            prop.load(input);

            this.address = prop.getProperty("server.address");
            this.domainName = prop.getProperty("server.domainName");
            this.port = Integer.parseInt(prop.getProperty("server.port"));
            this.workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
            this.sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
            this.documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
            
            Properties mimeProp = new Properties();
            mimeProp.load(Files.newInputStream(Paths.get(prop.getProperty("server.mimeConfig"))));
            for(String p : mimeProp.stringPropertyNames()) {
            	mimeTypes.put(p, mimeProp.getProperty(p));
            }
            
            loadWorkers(prop.getProperty("server.workers"));
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        	System.out.println(ex.getMessage());
        }
	}
	
	/**
	 * 	Starts the server.
	 */
	protected synchronized void start() {
		if(serverThread == null) {
			serverThread = new ServerThread();
		}
		if(!serverThread.isAlive()) {
			serverThread.start();			
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		SessionRemovalThread srThread = new SessionRemovalThread();
		srThread.start();
	}

	/**
	 * 	Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		threadPool.shutdown();
	}

	/**
	 * 	Loads the workers from the given path to the workers map.
	 * 	@param filePath Path to worker.properties file.
	 * 	@throws IllegalArgumentException If there is errors in worker.properties file.
	 */
	private void loadWorkers(String filePath) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		List<String> lines = Files.readAllLines(Paths.get(filePath));
		for(var line : lines) {
			String[] split = line.split("=");
			if(split.length > 2) {
				throw new IllegalArgumentException("Worker properties has illegal format.");
			}
			String path = split[0].strip();
			String fqcn = split[1].strip();
			
			if(workersMap.containsKey(path)) {
				throw new IllegalArgumentException("Duplicate path in worker properties.");
			}
			
			workersMap.put(path, loadWebWorker(fqcn));
		}
	}
	
	
	/**
	 * 	Loads the class with the given name, makes an instance of it
	 * 	and returns it. The class should implement the {@link IWebWorker}
	 * 	interface.	
	 * 	@param name Class to load.
	 * 	@return An instance of the loaded class.
	 */
	private IWebWorker loadWebWorker(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(name);
		Object newObject = referenceToClass.newInstance();
		IWebWorker iww = (IWebWorker) newObject;
		return iww;
	}
	
	/**
	 * 	Class which represents a {@link Thread} which listens
	 * 	for client connections on the server port, accepts the connections
	 * 	and submits them to the thread pool for execution.
	 */
	protected class ServerThread extends Thread{
	
		/**Flag which signals when the thread should be stopped*/
		private volatile boolean stop = false;
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void run() {
			try(ServerSocket serverSocket = new ServerSocket()){
				serverSocket.bind(new InetSocketAddress(address, port));
				while(!stop) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException ex) {
				throw new RuntimeException(ex.getMessage());
			} 
		}
		
		/**
		 *  Terminates this thread.
		 */
		public void terminate() {
			stop = true;
		}
	}
	
	private class SessionRemovalThread extends Thread{
		
		/**
		 * 	Constructs a new daemonic {@link SessionRemovalThread}.
		 */
		public SessionRemovalThread() {
			super();
			setDaemon(true);
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void run() {					
			long timeToSleep = 300 * 1000;
			long start, end, slept;
			
			while(true) {
				//delete entries
				for(var ses : sessions.keySet()) {
					if(sessions.get(ses).validUntil < System.currentTimeMillis() / 1000) {
						sessions.remove(ses);
					}
				}
				
				//sleep
			    while(timeToSleep > 0){
			        start = System.currentTimeMillis();
			        try{
			            Thread.sleep(timeToSleep);
			            break;
			        }
			        catch(InterruptedException e){
			            //work out how much more time to sleep for
			            end = System.currentTimeMillis();
			            slept = end - start;
			            timeToSleep -= slept;
			        }
			    }
			}
		}
	}

	/**
	 *  Class which holds information about a session.
	 */
	private static class SessionMapEntry{
		
		/**Session ID*/
		private String sid;
		
		/**Host*/
		private String host;
		
		/**How long should the session be valid.*/
		private long validUntil;
		
		/**Map for storing client data.*/
		private Map<String, String> map = new ConcurrentHashMap<>();
		
		/**
		 * 	Constructs a new {@link SessionMapEntry}.
		 * 	@param sid Session ID.
		 * 	@param host Host.
		 * 	@param validUntil How long is the session valid.
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
		}
		
	}
	
	/**
	 * 	Creates a randomly generated session ID which consists
	 * 	of 20 random uppercase letters.
	 * 	@return A random session ID.
	 */
	private String createSessionID() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 20; ++i) {
			sb.append((char)(sessionRandom.nextInt(26) + 'A'));				
		}
		return sb.toString();
	}
	
	/**
	 * 	Class which represents a client and his request that needs 
	 * 	to be processed.
	 */
	private class ClientWorker implements Runnable, IDispatcher{
		
		/**Socket at which the client is connected.*/
		private Socket csocket;
		
		/**Client input stream*/
		private PushbackInputStream istream;
		
		/**Client output stream*/
		private OutputStream ostream;
		
		/**Html version the client requested*/
		private String version;
		
		/**Method that the client requested.*/
		private String method;
		
		/**Host*/
		private String host;
		
		/**Map of parameters.*/
		private Map<String, String> params = new HashMap<>();
		
		/**Map of temporary parameters.*/
		private Map<String, String> tempParams = new HashMap<>();
		
		/**Map of permanent parameters.*/
		private Map<String, String> permParams = new HashMap<>();
		
		/**List of output cookies.*/
		private List<RCCookie> outputCookies = new ArrayList<>();
		
		/**Session ID*/
		private String SID;
		
		/**{@link RequestContext} of this client.*/
		private RequestContext context = null;
		
		/**
		 * 	Constructs a new {@link ClientWorker} at the given {@link Socket}.
		 * 	@param csocket {@link Socket} to which the client is connected.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
		
				//read request
				byte[] request = readRequest();
				if(request == null) {
					sendError(400, "Bad request");
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);
				
				//extract headers
				List<String> headers = extractHeaders(requestStr);			
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if(firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}
				
				//check if request has valid headers
				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(400, "Bad request");
					return;
				}
				version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendError(505, "HTTP Version Not Supported");
					return;
				}
				
				//set host
				for(var head : headers) {
					if(head.toLowerCase().startsWith("host:")) {
						host = head.replaceFirst("Host:", "").trim();
						if(host.matches(".+:\\d+")) {
							host = host.replaceAll(":\\d+", "");
						}
						break;
					}
				}
				if(host == null || host.isBlank()) {
					host = domainName;
				}
				
				checkSession(headers);
				
				//extract path and parameters
				String path; String paramString;
				String[] splitted = firstLine[1].split("\\?");
				path = splitted[0];
				if(splitted.length > 1) {					
					paramString = splitted[1];
					parseParameters(paramString);
				}				
				
				internalDispatchRequest(path, true);
				
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 *  Actual method which dispatches requests to various web workers.
		 *  @param urlPath Path which needs to be processed.
		 *  @param directCall Flag which signals if the client called the method or some of the workers.
		 *  @throws Exception if something terrible happens.
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception{
			
			if(urlPath.startsWith("/private") && directCall == true) {
				sendError(404, "File not found");
				return;
			}
		
			if(urlPath.startsWith("/ext/")) {
				IWebWorker iww = loadWebWorker("hr.fer.zemris.java.webserver.workers." + urlPath.substring(5).strip());
				
				setCodeAndText(200, "OK");
				iww.processRequest(context);
				return;
			}
			
			if(urlPath.equals("/calc")) {
				IWebWorker iww = loadWebWorker("hr.fer.zemris.java.webserver.workers.SumWorker");
				
				setCodeAndText(200, "OK");
				iww.processRequest(context);
				return;
			}
			
			
			if(urlPath.equals("/index2.html")) {
				IWebWorker iww = loadWebWorker("hr.fer.zemris.java.webserver.workers.HomeWorker");
				setCodeAndText(200, "OK");
				iww.processRequest(context);
				return;
			}
			
			if(urlPath.equals("/setbgcolor")) {
				IWebWorker iww = loadWebWorker("hr.fer.zemris.java.webserver.workers.BgColorWorker");
				setCodeAndText(200, "OK");
				iww.processRequest(context);
				return;
			}
			
			if(workersMap.containsKey(urlPath)) {
				setCodeAndText(200, "OK");
				workersMap.get(urlPath).processRequest(context);
				return;
			}
			
			
			//check if requested path is child of documentRoot
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));
			if(!requestedPath.toAbsolutePath().startsWith(documentRoot.toAbsolutePath())) {
				sendError(403, "Forbidden");
				return;
			}
			
			//check if requested path is a valid file
			if(!Files.exists(requestedPath) 	||
			   !Files.isReadable(requestedPath) ||
			   !Files.isRegularFile(requestedPath)) {
					sendError(404, "File not found");
					return;
			}
			
			//if extension is smart script, execute it
			String extension = urlPath.substring(urlPath.lastIndexOf(".") + 1);
			if(extension.equals("smscr")) {
				try {
					String script = Files.readString(requestedPath);
					SmartScriptParser parser = new SmartScriptParser(script);
					DocumentNode docNode = parser.getDocumentNode();
					setCodeAndText(200, "OK");
					SmartScriptEngine engine = new SmartScriptEngine(docNode, context);
					engine.execute();
				} catch(IOException | SmartScriptParserException ex) {
					sendError(500, "INTERNAL SERVER ERROR");
				}
			//if not set mimeType and send data
			} else {
				String mimeType = mimeTypes.get(extension);
				if(mimeType == null) {
					mimeType = "application/octet-stream";
				}
				setCodeAndText(200, "OK");
				context.setMimeType(mimeType);
				
				sendData(context, requestedPath);
			}
		}

		/**
		 *	Method which writes the data found at the given {@link Path}
		 *	to the {@link RequestContext}.
		 *	@param rc {@link RequestContext} to which the data should be written.
		 *	@param path {@link Path} to file which contains the data.
		 *	@throws IOException If there is an error writing or reading data.
		 */
		private void sendData(RequestContext rc, Path path) throws IOException{	
			InputStream fis = new BufferedInputStream(Files.newInputStream(path));
			byte[] buf = new byte[1024];
			while(true) {
				int r = fis.read(buf);
				if(r < 1) {
					break;
				}
				rc.write(buf, 0, r);				
			}
			fis.close();
		}
		
		/**
		 *  Goes through headers to find the session ID (sid) cookie
		 *  and if it doesn't find it, then it will create it.
		 * 	@param headers {@link List} of headers
		 */
		private synchronized void checkSession(List<String> headers) {
			String sidCandidate = null;

			//find cookie header
			for(var line : headers) {
				if(!line.toLowerCase().startsWith("cookie: ")) {
					continue;
				}
				
				//find sid cookie
				String[] split = line.substring(7).strip().split(";");
				for(var cookie : split) {
					if(cookie.startsWith("sid=")) {
						sidCandidate = cookie.split("=")[1];
						if(sidCandidate.startsWith("\"")) {
							sidCandidate = sidCandidate.substring(1, sidCandidate.length() - 1);
						}
						break;
					}
				}
				break;
			}
          
			SessionMapEntry sessionEntry;
			//if there is no sid cookie, create one
			if(sidCandidate == null) {
				sidCandidate = createSessionID();
				sessionEntry = createSessionEntry(sidCandidate);
			//if there is, check if valid and create new if not
			} else {
				sessionEntry = sessions.get(sidCandidate);
				if(sessionEntry != null) {
					if(!sessionEntry.host.equals(host)) {
						sessionEntry = createSessionEntry(sidCandidate);
					} else if(sessionEntry.validUntil < System.currentTimeMillis() / 1000) {
						sessions.remove(sidCandidate);
						sessionEntry = createSessionEntry(sidCandidate);
					}
				} else {
					sessionEntry = createSessionEntry(sidCandidate);
				}
				
				sessions.get(sidCandidate).validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
				permParams = sessions.get(sidCandidate).map;
			}
			
		}

		/**
		 * 	Creates a session entry for the given session ID and puts it
		 * 	into session {@link Map} and outputCookies {@link List}.
		 * 	@param sidCandidate Session ID of the entry to create.
		 */
		private synchronized SessionMapEntry createSessionEntry(String sidCandidate) {
			SessionMapEntry entry = new SessionMapEntry(sidCandidate, host, System.currentTimeMillis() / 1000 + sessionTimeout);
			sessions.put(sidCandidate, entry); 
			outputCookies.add(new RCCookie("sid", sidCandidate, null, host, "/"));
			return entry;
		}
		
		/**
		 *	Creates the {@link RequestContext} if context is <code>null</code>
		 *	and sets the status code and status text.
		 *	@param code Status code to set.
		 *	@param text Status text to set. 
		 */
		private void setCodeAndText(int code, String text) {
			if(context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			}		
			context.setStatusCode(code);
			context.setStatusText(text);
		}

		/**
		 * 	Constructs a header with the given status code and status text and
		 * 	writes it to the {@link OutputStream} of this {@link ClientWorker}.
		 * 	@param statusCode Error status code.
		 * 	@param statusText Text describing status code.
		 * 	@throws IOException If there is an error while writing to {@link OutputStream}.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(
				("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
				"Server: simple java server\r\n" +
				"Content-Type: text/plain;charset=UTF-8\r\n" +
				"Content-Length: 0\r\n" +
				"Connection: close\r\n" +
				"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
			ostream.flush();
		}

		/**
		 * 	Reads the given request.
		 * 	@return byte[] containing the read request.
		 */
		private byte[] readRequest() throws IOException {
		
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int state = 0;
		l:		while(true) {
					int b = istream.read();
					if(b==-1) return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				return bos.toByteArray();
			}

		/**
		 *	Extracts the headers from the given {@link String} which
		 *	contains all the headers.
		 *	@param requestHeader {@link String} containing the headers.
		 *	@return {@link List} of extracted headers. 
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * 	Parses the parameters from the given part of the url and puts
		 * 	them in the parameters {@link Map} of this {@link ClientWorker}.
		 * 	The parameters should be divided with & and consist of
		 * 	parameter name and parameter value which and an = sign
		 *  between them.
		 *  @param paramString String containing the parameters to parse.
		 */
		private void parseParameters(String paramString) {
			String[] split = paramString.split("&");
			for(String s : split) {
				String[] toPut = s.split("=");
				params.put(toPut[0], toPut[1]);
			}
		}	
	}
	
	/**
	 * 	Main method which creates and starts the server.
	 * 	@param args Path to server.properties.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Expected path to server.properties.");
			return;
		}
		
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
		System.out.println("Server started at port " + server.port);
				
	}
	
}
