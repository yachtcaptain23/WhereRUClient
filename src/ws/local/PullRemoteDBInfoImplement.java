package ws.local;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class PullRemoteDBInfoImplement implements PullRemoteDBInfo{
    
	private Socket _clientSocket = null;
    private Scanner in = null;
    private PrintWriter out = null;
    private Scanner stdIn = null;
    private Thread t3 = null;
    private String SERVER_URL = "192.168.1.120";
    private int SERVER_PORT = 4441; // Needs to be changed
    
	@Override
	public void run() {
		// Start reading from server
		try {
			_clientSocket = openCommunication(SERVER_URL, SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		// Close connection with the server
		try {
			closeCommunication(_clientSocket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Socket openCommunication(String url, int port) throws IOException {
			return new Socket(url, port);
	}

	@Override
	public boolean closeCommunication(Socket sock) throws IOException {
		sock.close();
		return true;
	}

	@Override
	public boolean processEntry(String line, String database) {
		// TODO Auto-generated method stub
		return false;
	}

}
