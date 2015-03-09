package entities;


/**
 * Groups link all the 
 * @author albertw
 *
 */
public interface GroupsInterface {

	/**
	 * Add a contact to a group
	 * Will have to get the user_id, group_id from the DB
	 * @param first_name First name
	 * @param last_name Last name
	 * @param group group name
	 * @return True if successfully added to the group
	 */
	boolean addContact(String first_name, String last_name, String group);
	
	/**
	 * Remove a contact from a group
	 * Will have to get the user_id, group_id from the DB
	 * @param first_name First name
	 * @param last_name Last name
	 * @param group group name
	 * @return True if successfully remove from the group
	 */
	boolean removeContact(String first_name, String last_name, String group);
	
	/**
	 * Gets the group_id from the group name
	 * @param group_name group name
	 * @return Group ID
	 */
	int getGroupID(String group_name);
	
	
}
