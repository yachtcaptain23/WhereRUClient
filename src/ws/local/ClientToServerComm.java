package ws.local;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientToServerComm implements Runnable{

	
	private Socket _clientSocket = null;
    private Scanner in = null;
    private PrintWriter out = null;
    private String SERVER_URL = "192.168.1.120";
    private int SERVER_PORT = 4444;
    public volatile String _command;
    private volatile boolean running = true;
    
    
	@Override
	public void run() {
		String fromServer;
		this._command = "";
		InputStreamReader isr = null;
		// Opens a stream to the server
		
		
		try {
			_clientSocket = new Socket(SERVER_URL, SERVER_PORT);
			
			
			
			out = new PrintWriter(_clientSocket.getOutputStream(), true);
			isr = new InputStreamReader(_clientSocket.getInputStream());
			in = new Scanner(new InputStreamReader(_clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Failed to connect!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to connect!");
		}
		System.out.println("Connected???");
		
		while(this.running){

			
			try {
				if(isr.ready()){
					fromServer = in.nextLine();
					System.out.println("Client received: " + fromServer);
					if(fromServer.equals("exit")){
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!this._command.equals("")){
				System.out.println("Starting to send a command!");
				out.println(this._command);
				this._command = "";
			}
		}
		
		System.out.println("ClientToServerComm Terminated!");
		out.close();
		in.close();
		
        try {
            _clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}
	
	public void sendCommand(String command){
		this._command = command;
	}
	
	public void terminate(){
		System.out.println("Terminating");
		this.running = false;
	}

}
