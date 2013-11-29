package DBLayout;

import java.util.ArrayList;

import entities.Contact;
import entities.Group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	// Database Version
	protected static final int DATABASE_VERSION = 1;
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
		
		db.execSQL("CREATE TABLE " + GROUP_TABLE_NAME + "( "  
		        + COLUMN_GROUPNAME +  " TEXT )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE_NAME);
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
		values.putNull(DBHelper.COLUMN_LATITUDE);
		values.putNull(DBHelper.COLUMN_LONGITUDE);
		values.putNull(DBHelper.COLUMN_MESSAGE);
		values.put(DBHelper.COLUMN_SHARE, true);
		
		db.insert(DBHelper.CONTACT_TABLE_NAME, null, values);
		db.close();
	}
	
	public void readContact(int id){
		/* 
		 * Read information of a contact entry from database
		 */
	}
	
	public void updateContact(int id, String first_name, String last_name, 
			double Latitude, double Longitude){
		/* 
		 * Update information of a contact entry in database
		 */
	}
	
	public void deleteContact(int id){
		/* 
		 * Delete a contact entry in database
		 */
	}
	
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
	
	public Boolean int_to_boolean(int arg){
		if (arg == 1)
			return true;
		else return false;
	}
}
