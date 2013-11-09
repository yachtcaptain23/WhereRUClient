package ws.remote;

import java.net.Socket;

/**
 * Opens a socket with agreed upon values
 * @author albertw
 *
 */
public interface PushLocalDBInfo {
	/**
	 * Opens communication with the client
	 */
	Socket openCommunication(String url, int port);
	
	/**
	 * Close communication with the client
	 */
	boolean closeCommunication(Socket sock);
	
	/**
	 * Pushes line to client
	 * Gets entry from CRUD
	 * @param line Entry line
	 * @return True if successful
	 */
	boolean pushEntry(String line, String database);
	
}
