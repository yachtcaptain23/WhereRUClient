package ws.remote;

import java.net.Socket;

/**
 * Pulls data from the server and updates the entries inside the database
 * Must extend SQLiteOpenHelper
 * @author albertw
 *
 */
public interface PullRemoteDBInfo extends Runnable{
	/**
	 * Opens communication with the client
	 */
	Socket openCommunication(String url, int port);
	
	/**
	 * Close communication with the client
	 */
	boolean closeCommunication(Socket sock);
	
	/**
	 * Receives an entry line from client and updates the database(s) with CRUD
	 * Entry format:
	 * TODO
	 * @param line Entry line
	 * @return True if successful
	 */
	boolean processEntry(String line, String database);
}
