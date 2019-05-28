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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 *	SmartHttpServer TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class SmartHttpServer {

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	
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

        } catch (IOException ex) {
        	throw new IllegalArgumentException("Cannot read properties from given file.");
        }
	}
	
	protected synchronized void start() {
		serverThread = new ServerThread();
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}
	
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}
	
	protected class ServerThread extends Thread{
	
		/**
		 *	{@inheritDoc}
		 */
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				while(true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException ex) {
				//TODO kako postupiti u slucaju exceptiona
				throw new RuntimeException(ex.getMessage());
			}
		}
		
	}
	
	private class ClientWorker implements Runnable, IDispatcher{
		
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<>();
		private Map<String, String> tempParams = new HashMap<>();
		private Map<String, String> permParams = new HashMap<>();
		private List<RCCookie> outputCookies = new ArrayList<>();
		private String SID;
		
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		
		public void internalDispatchRequest(String urlPath, boolean directCall)
			throws Exception{
			
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));
			//TODO if requestedPath is not below documentRoot, return response status 403 forbidden
			if(!Files.exists(requestedPath) &&
			   !Files.isReadable(requestedPath) &&
			   !Files.isRegularFile(requestedPath)) {
					sendError(404, "File not found");
					return;
			}
			
			String extension = urlPath.substring(urlPath.lastIndexOf(".") + 1);
			String mimeType = mimeTypes.get(extension);
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
			//TODO jesu li permParams persistentParameters?
			RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
			rc.setMimeType(mimeType);
			rc.setStatusCode(200);
			rc.setStatusText("OK");
			
			sendData(rc, requestedPath);

		}
		
		private void sendData(RequestContext rc, Path data) {
			
			try(InputStream fis = new BufferedInputStream(Files.newInputStream(data))){
				byte[] buf = new byte[1024];
				while(true) {
					int r = fis.read(buf);
					if(r < 1) {
						break;
					}
					rc.write(buf, 0, r);				
				}
			} catch (IOException ex) {
				// TODO: handle exception
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
		 *	{@inheritDoc}
		 */
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				byte[] request = readRequest();
				if(request==null) {
					sendError(400, "Bad request");
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);
				
				List<String> headers = extractHeaders(requestStr);			
			
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if(firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}
	
				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(400, "Bad request");
					return;
				}
				
				version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendError(400, "Bad request");
					return;
				}
				//TODO ako se dogodi iznimka zatvori konekciju i to je to
				//TODO parser execption status 500 internal server error
				//TODO u engine ne treba loviti iznimke
				for(var head : headers) {//TODO case insensitive
					if(head.startsWith("Host:")) {
						host = head.replaceFirst("Host:", "").trim();
						if(host.matches(".+:\\d+")) {
							host = host.replaceAll(":\\d+", "");
						}
					}
					break;
				}
				if(host == null || host.isBlank()) {
					host = domainName;
				}
				
				String path; String paramString;
				String[] splitted = firstLine[1].split("\\?");
				path = splitted[0];
				if(splitted.length > 1) {					
					paramString = splitted[1];
					parseParameters(paramString);
				}				
				
				internalDispatchRequest(path, true);
				
				csocket.close();
				
			} catch (IOException e) {
				// TODO catch IOException
			} catch(Exception e) {
				// TODO catch expcetion
			}
		}
		
		private void parseParameters(String paramString) {
			String[] split = paramString.split("&");
			for(String s : split) {
				String[] toPut = s.split("=");
				params.put(toPut[0], toPut[1]);
			}
		}
		
		
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
		
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Expected path to server.properties.");
			return;
		}
		
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}
	
}
