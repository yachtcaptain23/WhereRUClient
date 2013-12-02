package ws.local;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import android.content.Context;

import DBLayout.DBHelper;

public class ClientToServerComm implements Runnable{

	private static ClientToServerComm instance = null;
	private Socket _clientSocket = null;
    private Scanner in = null;
    private PrintWriter out = null;
    private String SERVER_URL = "128.237.123.59";
    private int SERVER_PORT = 4445;
    public volatile String _command;
    private volatile boolean running;
    private volatile boolean pull_request;
    public static boolean editing_contacts = false;
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
		String fromServer;
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
			System.out.println("Should be terminating... why is it not???");
			return false;
		}
		
		if(input.startsWith("CONTACT:")){
			editing_contacts = true;
		}
		else if(editing_contacts == true){
			
			str = input.split(",");
			System.out.println("Adding... " + str[0] + str[1]);
			
			dbHelper.insertContact(str[0],str[1],Double.parseDouble(str[2])
					,Double.parseDouble(str[3])
					,str[4],Integer.parseInt(str[5]));
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
	 * 
	 */
	public void pullGroup(){
		pull_request = true;
		DBHelper dbHelper = new DBHelper(_context);
		String pull = "Qsql:SELECT * FROM CONTACT WHERE GROUPNAME=" + 
				"\"" + dbHelper.readMyselfGroup() +"\";";
		dbHelper.close();
		sendCommand(pull);
	}
	
	/**
	 * Pushes data to the server
	 * @param name
	 * @param groupname
	 * @param latitude
	 * @param longitude
	 * @param message
	 * @param share
	 */
	public void pushMyself(){
		
		DBHelper dbHelper = new DBHelper(_context);
		//dbHelper.instantiateMyself("Nemo");
		//dbHelper.setMyselfGroup("ASA");
		String [] all = dbHelper.readMyself().split(",");
		String name = all[0];
		String groupname = all[1];
		double latitude = Double.parseDouble(all[2]);
		double longitude = Double.parseDouble(all[3]);
		String message = all[4];
		int share = 1;
		
		String push = "Esql:DELETE FROM CONTACT WHERE NAME=\""+name + "\";";
		push = push + "INSERT INTO CONTACT VALUE(";
		push = push + "\"" + name+"\",\""+groupname+"\","+latitude+","+longitude+",\""+message+"\","+share + ");";
		dbHelper.close();		
		sendCommand(push);
		
	}
	
	public void terminate(){
		System.out.println("Asking thread to terminate");
		this.running = false;
	}

}

