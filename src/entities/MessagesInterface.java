package entities;

import java.util.ArrayList;

/**
 * Gets the messages
 * @author albertw
 *
 */
public interface MessagesInterface {
	
	/**
	 * Gets a list of messages from the database sorted by date
	 * Search via via ContactsInterface
	 * @param fname First name
	 * @param lname Last name
	 * @return ArrayList of messages (String(s))
	 */
	ArrayList<String> getReceivedMessages(String fname, String lname);
	
	/**
	 * Gets a list of sent message sfrom the database sorted by date
	 * Search via ContactsInterface
	 * @param fname First name
	 * @param lname Last name
	 * @return ArrayList of messages (String(s))
	 */
	ArrayList<String> getSentMessages(String fname, String lname);
	
	/**
	 * Send a message to another user
	 * Has to do database lookup for sender id and contact id via ContactsInterface
	 * @return True if sender and recipient exist
	 */
	boolean sendMessage(String sender_fname, String sender_lname, String recipient_fname, String recipient_lname, String message);
	
	/**
	 * Send a message to a group
	 * 
	 * @param sender_fname Sender first name
	 * @param sender_lname Sender last name 
	 * @param group_name Sender group name
	 * @param message the message
	 * @return True if group and sender exists
	 */
	boolean sendMessageToGroup(String sender_fname, String sender_lname, String group_name, String message);
	

}
