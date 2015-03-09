package ws.local;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import android.content.Context;

import DBLayout.DBHelper;

/**
 * Communicates with the server by opening up a socket and closing it
 * Server will close and reopen and listen for attempted communication
 * Each client must only connect to one Port to prevent signal collisions
 * @author albertw
 *
 */
public class ClientToServerComm implements Runnable{

	private static ClientToServerComm instance = null;
	private Socket _clientSocket = null;
    private Scanner in = null;
    private PrintWriter out = null;
    private String SERVER_URL = "128.237.175.48";
    private int SERVER_PORT = 4445;
    public volatile String _command;
    private volatile boolean running;
    private volatile boolean pull_request;
    public static boolean editing_contacts = false;
    public static boolean editing_messages = false;
    private static Context _context;
    
    
    /**
     * ONLY USE THIS METHOD FOR INSTANTIATING (creates a singleton)
     * @return
     */
    public static synchronized ClientToServerComm getInstance(Context context){
    	_context = context;
    	if (instance == null){
    		instance = new ClientToServerComm();
    	}
    	return instance;
    }
    
	@Override
	public void run() {
		this._command = "";
		this.running = true;
		this.pull_request = false;
		
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
		
		while(this.running || pull_request){
			
			try {
				if(isr.ready()){
					while(in.hasNext()){
						this.pull_request = processServerInput(in.nextLine());
						if(!this.pull_request) break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e){
				this.running = false;
				
			}
			
			
			if(!this._command.equals("")){
				System.out.println("Starting to send a command!");
				out.println(this._command);
				this._command = "";
			}
			
		}
		
		out.println("RESTART_CONNECTION");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
	/**
	 * Processes the input from the server
	 * @param input contains the input
	 */
	public static boolean processServerInput(String input){
		String [] str;
		DBHelper dbHelper = new DBHelper(_context);
		System.out.println("Got input:" +input);
		
		if(input.equals("END_PULL")){
			editing_contacts = false;
			editing_messages = false;
			System.out.println("Should be terminating... why is it not???");
			return false;
		}
		
		if(input.startsWith("CONTACT:")){
			editing_contacts = true;
		}
		else if(editing_contacts == true){
			
			str = input.split(",");
			System.out.println("Adding... " + str[0] + str[1]);
			
			/*dbHelper.insertContact(str[0],str[1],Double.parseDouble(str[2])
					,Double.parseDouble(str[3])
					,str[4],Integer.parseInt(str[5]));*/
			
			dbHelper.updateContact(str[0],Double.parseDouble(str[1])
					,Double.parseDouble(str[2])
					,Integer.parseInt(str[3]));
		}
		
		if(input.startsWith("MESSAGE:")){
			editing_messages = true;
		}
		else if(editing_messages == true){
			str = input.split(",");
			System.out.println("Adding... " + str[0] + str[1]);
			
			dbHelper.updateMessage(str[1], str[0]);
		}
		dbHelper.close();

		return true;
		//String [] lines = input.split("\n");
		
	}
	
	/**
	 * Sends command to the server
	 * @param command the command to be sent
	 */
	public void sendCommand(String command){
		this._command = command;
	}
	
	/**
	 * Sends a pull request to the server
	 */
	public void pullAll(){
		pull_request = true;
		String pull = "Qsql:SELECT * FROM CONTACT;";
		sendCommand(pull);
	}
	
	/**
	 * Pulls all messages from the server
	 */
	public void pullMessage(){
		pull_request = true;
		
		DBHelper dbHelper = new DBHelper(_context);
		
		String [] all = dbHelper.readMyself().split(",");
		String receiver = all[0];
		
		dbHelper.close();
		String pull = "Qsql:SELECT CONTENT, SENDER FROM MESSAGE WHERE RECEIVER = \"" + receiver + "\";";
		sendCommand(pull);
	}
	

	/**
	 * Pushes data to the server
	 */
	public void pushMyself(){
		
		DBHelper dbHelper = new DBHelper(_context);
		String [] all = dbHelper.readMyself().split(",");
		String name = all[0];
		double latitude = Double.parseDouble(all[1]);
		double longitude = Double.parseDouble(all[2]);
		int share = Integer.parseInt(all[3]);
		
		String push = "Esql:UPDATE CONTACT SET LATITUDE = " + latitude + ", LONGITUDE = " + longitude + ", SHARE = " + share + " WHERE NAME = \"" + name + "\";";
		dbHelper.close();		
		sendCommand(push);
		
	}
	
	/**
	 * Pushes message from the server corresponding to the receiver
	 * @param content message content
	 * @param receiver receiver
	 */
	public void pushMessage(String content, String receiver){
		DBHelper dbHelper = new DBHelper(_context);
		
		String [] all = dbHelper.readMyself().split(",");
		String sender = all[0];
		
		dbHelper.close();
		
		String push = "Esql:DELETE FROM MESSAGE WHERE SENDER =\"" + sender + "\" AND RECEIVER = \"" + receiver + "\";";
		push = push + "INSERT INTO MESSAGE VALUES(\"" + content + "\", \"" + sender +"\", \"" + receiver + "\");";
		sendCommand(push);
	}
	
	/**
	 * Creates a on the server contact with the following name
	 * @param name name of the contact
	 */
	public void createContact(String name){
		String push = "Esql: INSERT INTO contact (name, share) VALUES (\"" + name + "\", true);";
		sendCommand(push);
	}
	
	/**
	 * Terminates this thread
	 */
	public void terminate(){
		System.out.println("Asking thread to terminate");
		this.running = false;
	}

}

