package ws.local;

import java.net.Socket;

/**
 * Pulls data from the server and updates the entries inside the database
 * Must extend SQLiteOpenHelper
 * @author albertw
 *
 */
public interface PullRemoteDBInfo extends Runnable{
	/**
	 * Opens communication with the server
	 */
	Socket openCommunication(String url, int port);
	
	/**
	 * Close communication with the server
	 */
	boolean closeCommunication(Socket sock);
	
	/**
	 * Receives an entry line from server and updates the database(s) with CRUD
	 * Entry format:
	 * TODO
	 * @param line Entry line
	 * @return True if successful
	 */
	boolean processEntry(String line, String database);
}
