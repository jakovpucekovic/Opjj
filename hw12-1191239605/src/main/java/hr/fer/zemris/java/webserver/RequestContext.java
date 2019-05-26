package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *	RequestContext TODO javadoc
 * 
 * 	@author Jakov Pucekovic
 * 	@version 1.0
 */
public class RequestContext {

	private OutputStream outputStream;
	private Charset charset;
	
	private String encoding;
	private int statusCode;
	private String statusText;
	private String mimeType;
	private Long contentLength;
	
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated;
	
	
	/**
	 * 	Constructs a new {@link RequestContext} with the given parameters.
	 * 	@param outputStream {@link OutputStream} to write to.
	 * 	@param parameters {@link Map} of parameters or <code>null</code>.
	 * 	@param persistentParameters {@link Map} of persistent parameters or <code>null</code>.
	 * 	@param outputCookies {@link List} of {@link RCCookie}s or <code>null</code>.
	 * 	@throws NullPointerException If outputStream is <code>null</code>.
	 */
	public RequestContext(
			OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		
		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
		contentLength = null;
		headerGenerated = false;
		
		temporaryParameters = new HashMap<>();
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies ;
	
	}
	
	/**
	 * 	Sets the encoding of the {@link RequestContext}.
	 * 	@param encoding the encoding to set.
	 * 	@throws RuntimeException If header is already generated.
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated.");
		}
		this.encoding = encoding;
	}

	/**
	 * 	Sets the statusCode of the {@link RequestContext}.
	 * 	@param statusCode the statusCode to set.
	 *	@throws RuntimeException If header is already generated.
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * 	Sets the statusText of the {@link RequestContext}.
	 * 	@param statusText the statusText to set.
	 *	@throws RuntimeException If header is already generated.
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated.");
		}
		this.statusText = statusText;
	}

	/**
	 * 	Sets the mimeType of the {@link RequestContext}.
	 * 	@param mimeType the mimeType to set.
	 *	@throws RuntimeException If header is already generated.
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * 	Sets the contentLength of the {@link RequestContext}.
	 * 	@param contentLength the contentLength to set.
	 *	@throws RuntimeException If header is already generated.
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException("Header already generated.");
		}
		this.contentLength = contentLength;
	}
	
	/**
	 * 	Returns the value stored in the parameters map with the given name.
	 * 	@param name Name of the stored parameter.
	 * 	@return Value of the stored parameter.
	 */
	public String getParameter(String name) {
		String ret = parameters.get(name);
		return ret;
	}
	
	/**
	 * 	Returns an unmodifiable {@link Set} of all parameter names.
	 * 	@return A {@link Set} of all parameter names.
	 */
	public Set<String> getParameterNames(){
		return Collections.unmodifiableSet(parameters.keySet()); 
	}
	
	/**
	 * 	Returns the value stored in the persistent parameters map with the given name.
	 * 	@param name Name of the stored persistent parameter.
	 * 	@return Value of the stored persistent parameter.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * 	Returns an unmodifiable {@link Set} of all persistent parameter names.
	 * 	@return A {@link Set} of all persistent parameter names.
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * 	Adds a new persistent parameter with the given name and value.
	 * 	@param name Name of the new persistent parameter.
	 * 	@param value Value of the new persistent parameter.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * 	Deletes the persistent parameter with the given name.
	 * 	@param name Name of the persistent parameter to delete.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * 	Returns the value stored in the temporary parameters map with the given name.
	 * 	@param name Name of the stored temporary parameter.
	 * 	@return Value of the stored temporary parameter.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * 	Returns an unmodifiable {@link Set} of all temporary parameter names.
	 * 	@return A {@link Set} of all temporary parameter names.
	 */
	public Set<String> getTemporaryParametersNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * 	Returns an identifier which is unique for the current user session.
	 * 	@return ALWAYS RETURNS EMPTY STRING. TODO javadoc
	 */
	public String getSessionID() {
		return "";
	}
	
	/**
	 * 	Adds a new temporary parameter with the given name and value.
	 * 	@param name Name of the new temporary parameter.
	 * 	@param value Value of the new temporary parameter.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * 	Deletes the temporary parameter with the given name.
	 * 	@param name Name of the temporary parameter to delete.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	
	//TODO javadoc
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}
	
	/**
	 * 	Writes the given data to the {@link OutputStream} and creates 
	 * 	the appropriate header the first time any write method is called.
	 * 	Same as write(data, 0, data.lenght).
	 * 	@param data Data to write.
	 * 	@return this
	 * 	@throws IOException If there is an error when writing.
	 */
	public RequestContext write(byte[] data) throws IOException{
		return write(data, 0, data.length);
	}
	
	/**
	 * 	Writes the given length of the given data from the given offset
	 * 	to the {@link OutputStream} and creates the appropriate header
	 * 	the first time any write method is called.
	 * 	@param data Data to write.
	 * 	@param offset Offset in data.
	 * 	@param len Length of data to write.
	 * 	@return this
	 * 	@throws IOException If there is an error when writing.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException{
		if(!headerGenerated) {
			generateHeader();
		}
		
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}
	
	/**
	 * 	Writes the given text using the set charset to the {@link OutputStream}
	 *  and creates	the appropriate header the first time any write method is called.
	 * 	@param text Text to write.
	 * 	@return this
	 * 	@throws IOException If there is an error when writing.
	 */
	public RequestContext write(String text) throws IOException{
		return write(text.getBytes(charset));
	}
	
	/**
	 * 	Generates the appropriate header and writes it.
	 * 	@throws IOException If there is an error when writing.
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
		
		sb.append("Content-Type: ").append(mimeType);
		if(mimeType.startsWith("text")) {
			sb.append("; charset=").append(encoding);
		}
		sb.append("\r\n");
		
		if(contentLength != null) {
			sb.append("Content-Length: ").append(contentLength).append("\r\n");
		}
		
		if(!outputCookies.isEmpty()) {
			for(var cookie : outputCookies) {
				sb.append("Set-Cookie: ").append(cookie.name).append("=\"").append(cookie.value).append("\"");
				if(cookie.domain != null) {
					sb.append("; Domain=").append(cookie.domain);
				}
				if(cookie.path != null) {
					sb.append("; Path=").append(cookie.path);
				}
				if(cookie.maxAge != null) {
					sb.append("; Max-Age=").append(cookie.maxAge);
				}
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		
		headerGenerated = true;
		
		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();
	}
	
	
	
	
	/**
	 * 	TODO javadoc
	 */
	public static class RCCookie{
				
		/**Name of the {@link RCCookie}.*/
		private String name;
		/**Value of the {@link RCCookie}.*/
		private String value;
		/**Domain of the {@link RCCookie}.*/
		private String domain;
		/**Path of the {@link RCCookie}.*/
		private String path;
		/**Maximum age of the {@link RCCookie}.*/
		private Integer maxAge;
		
		/**
		 * 	Constructs a new {@link RCCookie} with the given parameters.
		 * 	@param name Name of the {@link RCCookie}.
		 * 	@param value Value of the {@link RCCookie}.
		 * 	@param domain Domain of the {@link RCCookie}.
		 * 	@param path Path of the {@link RCCookie}.
		 * 	@param maxAge Maximum age of the {@link RCCookie}.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * 	Returns the name of the {@link RCCookie}.
		 * 	@return the name of the {@link RCCookie}.
		 */
		public String getName() {
			return name;
		}
		/**
		 * 	Returns the value of the {@link RCCookie}.
		 * 	@return the value of the {@link RCCookie}.
		 */
		public String getValue() {
			return value;
		}
		/**
		 * 	Returns the domain of the {@link RCCookie}.
		 * 	@return the domain of the {@link RCCookie}.
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * 	Returns the path of the {@link RCCookie}.
		 * 	@return the path of the {@link RCCookie}.
		 */
		public String getPath() {
			return path;
		}
		/**
		 * 	Returns the maxAge of the {@link RCCookie}.
		 * 	@return the maxAge of the {@link RCCookie}.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	
	}
	
}
