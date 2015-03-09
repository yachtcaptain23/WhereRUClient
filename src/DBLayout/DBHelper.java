package DBLayout;

import java.util.ArrayList;

import entities.Contact;
import entities.Group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Interfaces with the entire program to store user information
 * and contact information
 * Activities and client-server handlers perform actions via
 * DBHelper objects
 * @author albertw
 *
 */

public class DBHelper extends SQLiteOpenHelper{
	// Database Version
	protected static final int DATABASE_VERSION = 3;
    // Database Name
	protected  static final String DATABASE_NAME = "WhereRU_db";
    
	protected  static final String CONTACT_TABLE_NAME = "contact";
	protected  static final String COLUMN_NAME = "name";
	protected  static final String COLUMN_GROUP = "groupname";
	protected  static final String COLUMN_LATITUDE = "latitude";
	protected  static final String COLUMN_LONGITUDE = "longitude";
	protected  static final String COLUMN_MESSAGE = "message";
	protected  static final String COLUMN_SHARE = "share";
	
    protected static final String GROUP_TABLE_NAME = "groups";
    protected static final String COLUMN_GROUPNAME = "groupname";
    
    protected static final String MYSELF_TABLE_NAME = "myself";
    
    protected static final String USERS_TABLE_NAME = "users";
    protected static final String COLUMN_PASSWORD = "password";
    
    // Constructor
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + CONTACT_TABLE_NAME + "( "  
		        + COLUMN_NAME + " TEXT, " + COLUMN_GROUP + " TEXT, "
		        + COLUMN_LATITUDE + " REAL, " + COLUMN_LONGITUDE + " REAL, "
		        + COLUMN_MESSAGE + " TEXT, " + COLUMN_SHARE + " BOOLEAN)");
		
		db.execSQL("CREATE TABLE " + MYSELF_TABLE_NAME + "( "  
		        + COLUMN_NAME + " TEXT, "
		        + COLUMN_LATITUDE + " REAL, " + COLUMN_LONGITUDE + " REAL, "
		        + COLUMN_SHARE + " BOOLEAN, "
		        + COLUMN_PASSWORD +	" TEXT)");
		
		db.execSQL("CREATE TABLE " + GROUP_TABLE_NAME + "( "  
		        + COLUMN_GROUPNAME +  " TEXT )");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + MYSELF_TABLE_NAME);
		onCreate(db);
	}
	
	// CRUD methods
	public void createContact(String name, String group){
		/* 
		 * Create a new entry in contact database using the information
		 * given by the parameters
		 */
		
		SQLiteDatabase db = this.getWritableDatabase();
		
    	ContentValues values = new ContentValues();
    	
		values.put(DBHelper.COLUMN_NAME, name);
		values.put(DBHelper.COLUMN_GROUP, group);
		
		/* Comment the following 3 lines if you want to hard code some contact info */
		values.putNull(DBHelper.COLUMN_LATITUDE);
		values.putNull(DBHelper.COLUMN_LONGITUDE);
		values.putNull(DBHelper.COLUMN_MESSAGE);
		/* If you want to hard code some contacts for test, uncomment the following */
		//values.put(DBHelper.COLUMN_LATITUDE, 40.44606);
		//values.put(DBHelper.COLUMN_LONGITUDE, -79.94803);
		//values.put(DBHelper.COLUMN_MESSAGE, "Stuying data structure");
		values.put(DBHelper.COLUMN_SHARE, true);
		
		db.insert(DBHelper.CONTACT_TABLE_NAME, null, values);
		db.close();
	}
	
	/**
	 * Returns a the myself row
	 * Contains name, long, lat, share, password
	 * @return MYSELF row
	 */
	public String readMyself(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM MYSELF", null); // Returns a cursor on a table
		if(!c.moveToFirst())
			System.out.println("Apparently the set is empty??");
		String s = c.getString(0); // Might be off by one
		s = s + "," +  c.getString(1);
		s = s + "," +  c.getString(2);
		s = s + "," +  c.getString(3);
		//s = s + "," +  c.getString(4);
		//s = s + "," +  c.getString(5);
		db.close();
		return s;
	}
	
	
	/**
	 * Updates the location of the MYSELF table
	 * @param Latitude contains the latitude
	 * @param Longitude contains the longitude
	 */
	public void updateMyselfLocation(double Latitude, double Longitude){
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	System.out.println("Writing " + Latitude + " " + Longitude);
    	values.put(COLUMN_LATITUDE, Latitude);
    	values.put(COLUMN_LONGITUDE, Longitude);
    	db.update(MYSELF_TABLE_NAME, values, null, null);
    	db.close();
	}
	
	/**
	 * Updates the contact within the database
	 * @param name name of the contact
	 * @param Latitude latitude stored as a double
	 * @param Longitude longitutde stored as a double
	 * @param share boolean value as to whether or not to share with the server
	 */
	public void updateContact(String name, double Latitude, double Longitude, int share){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
    	
		values.put(DBHelper.COLUMN_LATITUDE, Latitude);
		values.put(DBHelper.COLUMN_LONGITUDE, Longitude);
		values.put(DBHelper.COLUMN_SHARE, share);
		
		db.update(DBHelper.CONTACT_TABLE_NAME, values, DBHelper.COLUMN_NAME + "=?", new String[] {name});
		db.close();
	}
	
	/**
	 * Updates the messages with the MESSAGE table
	 * @param name name of the sender
	 * @param msg message to the recipient
	 */
	public void updateMessage(String name, String msg){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
    	
		
		values.put(DBHelper.COLUMN_MESSAGE, msg);
		
		db.update(DBHelper.CONTACT_TABLE_NAME, values, DBHelper.COLUMN_NAME + "=?", new String[] {name});
		db.close();
	}
	
	/**
	 * Deletes the contact from the MYSELF database
	 * @param name name of the contact
	 */
	public void deleteContact(String name){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from MYSELF where name=\""+name+"\";");
		db.close();
	}
	
	/**
	 * Creates a group with the group name
	 * @param group_name group name
	 */
	public void createGroup(String group_name){
		/* 
		 * Create a new entry in group database using the information
		 * given by the parameters
		 */
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
		values.put(DBHelper.COLUMN_GROUPNAME, group_name);
		
		db.insert(DBHelper.GROUP_TABLE_NAME, null, values);
		db.close();
	}
	
	/**
	 * Returns an ArrayList of all the groups
	 * @return 
	 */
	public ArrayList<Group> getAllGroups(){
		ArrayList<Group> groups = new ArrayList<Group>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(DBHelper.GROUP_TABLE_NAME,
				new String[] {DBHelper.COLUMN_GROUPNAME}, 
				null, null, null, null, null);
		
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      groups.add(new Group(cursor.getString(0)));
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    db.close();
	    
	    return groups;
	}
	
	/**
	 * Returns an ArrayList of Contacts
	 * @return
	 */
	public ArrayList<Contact> getAllContacts(){
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(DBHelper.CONTACT_TABLE_NAME,
				new String[] {DBHelper.COLUMN_NAME, DBHelper.COLUMN_GROUP,
				DBHelper.COLUMN_LATITUDE, DBHelper.COLUMN_LONGITUDE,
				DBHelper.COLUMN_MESSAGE, DBHelper.COLUMN_SHARE},
				null, null,
				null, null, null);
		
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      contacts.add(new Contact(cursor.getString(0), cursor.getString(1),
	    		  cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4),
	    		  int_to_boolean(cursor.getInt(5))));
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    db.close();
	    
	    return contacts;
	}
	
	/**
	 * Returns all Contacts with the group name
	 * @param groupname group name
	 * @return ArrayList of Contacts that belong to the specified group
	 */
	public ArrayList<Contact> getContactByGroup(String groupname){
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(DBHelper.CONTACT_TABLE_NAME,
				new String[] {DBHelper.COLUMN_NAME, DBHelper.COLUMN_GROUP,
				DBHelper.COLUMN_LATITUDE, DBHelper.COLUMN_LONGITUDE,
				DBHelper.COLUMN_MESSAGE, DBHelper.COLUMN_SHARE},
				DBHelper.COLUMN_GROUP + "=?", new String[]{groupname},
				null, null, null);
		
		cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      contacts.add(new Contact(cursor.getString(0), cursor.getString(1),
	    		  cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4),
	    		  int_to_boolean(cursor.getInt(5))));
	      cursor.moveToNext();
	    }
	    
	    cursor.close();
	    db.close();
	    
	    return contacts;
	}
	
	/**
	 * Converts an integer value to its corresponding boolean
	 * @param arg 1 or 0
	 * @return true or false
	 */
	public Boolean int_to_boolean(int arg){
		if (arg == 1)
			return true;
		else return false;
	}
	
	/**
	 * Replaces instantiateMyself()
	 * @param name
	 * @param password
	 */
	public void createUser(String name, String password){
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from MYSELF;"); //clears the table
    	db.execSQL("INSERT INTO MYSELF VALUES(\"" + name + "\",0,0,1,\""+ password + "\");");
    	db.close();
	}
	
	/**
	 * Gets the password from the database
	 * @param name
	 * @return
	 */
	public String getPassword(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM MYSELF", null); // Returns a cursor on a table
		if(!c.moveToFirst()){
			System.out.println("Apparently the set is empty??");
			return "itsfridayfridaygottagetdownonfriday";
		}
		String s = c.getString(4);
		db.close();
		return s;
	}
	
}
