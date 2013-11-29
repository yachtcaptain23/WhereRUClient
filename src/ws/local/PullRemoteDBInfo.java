package ws.local;

import java.io.IOException;
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
	 * @throws IOException 
	 */
	Socket openCommunication(String url, int port) throws IOException;
	
	/**
	 * Close communication with the server
	 * @throws IOException 
	 */
	boolean closeCommunication(Socket sock) throws IOException;
	
	/**
	 * Receives an entry line from server and updates the database(s) with CRUD
	 * Entry format:
	 * TODO
	 * @param line Entry line
	 * @return True if successful
	 */
	boolean processEntry(String line, String database);
}
