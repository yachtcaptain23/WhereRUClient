package entities;

import java.util.ArrayList;

/**
 * Contacts interfaces with the entire Application. Contacts contains the coordinates of each Contact
 * @author albertw
 *
 */
public interface ContactsInterface {

	/**
	 * Gets a list of Contact. For displaying each Contact on map using coordinates
	 * @return ArrayList of Contact
	 */
	ArrayList<Contact> getContacts();
	
	/**
	 * Remove a Contact from database
	 * @param first_name First name
	 * @param last_name Last name
	 * @return True if successful
	 */
	boolean removeContact(String first_name, String last_name);
	
	/**
	 * Search for a Contact
	 * @param first_name First name
	 * @param last_name Last name
	 * @return True if successful
	 */
	Contact searchContact(String first_name, String last_name);
	
	
	/**
	 * Searches for the unique Contact_id from the database
	 * @param first_name First name
	 * @param last_name last name
	 * @return Unique contact_id
	 */
	int getContactID(String first_name, String last_name);
	
	
	/**
	 * Add a Contact to database
	 * @param first_name First name
	 * @param last_name Last name
	 * @return True if successful
	 */
	boolean addContact(String first_name, String last_name);
	
	
	/**
	 * Contact contains the information for a contact
	 * @author albertw
	 *
	 */
	public interface Contact{
		/**
		 * Returns the first name from the database
		 * @param id unique_id
		 * @return first name
		 */
		String getFirstName(int id);
		
		/**
		 * Returns last name from the database
		 * @param id unique_id
		 * @return last name
		 */
		String getLastName(int id);
		
		/**
		 * Gets the latitude from the database
		 * @param id unique_id
		 * @return latitude
		 */
		int getLatitude(int id);
		
		/**
		 * Gets the longitude from the database
		 * @param id unique_id
		 * @return longitude
		 */
		int getLongitude(int id);
		
		/**
		 * Changes the latitude in the database
		 * @param id unique_id
		 */
		void setLatitude(int id);
		
		/**
		 * Changes the longitude in the database
		 * @param id unique_id
		 */
		void setLongitude(int id);
	}
}
