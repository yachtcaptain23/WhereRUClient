package ws.local;

import java.net.Socket;

/**
 * Opens a socket with the 
 * @author albertw
 *
 */
public interface PushLocalDBInfo {
	/**
	 * Opens communication with the server
	 */
	Socket openCommunication(String url, int port);
	
	/**
	 * Close communication with the server
	 */
	boolean closeCommunication(Socket sock);
	
	/**
	 * Pushes line to server
	 * Gets entry from CRUD
	 * @param line Entry line
	 * @return True if successful
	 */
	boolean pushEntry(String line, String database);
	
}
