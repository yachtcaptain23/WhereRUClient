package entities;

/**
 * Message object
 * Includes content, sender, and receiver
 * @author albertw
 *
 */
public class Message {
	
	private String content;
	private String sender;
	private String receiver;
	
	/* Constructor */
	public Message(String content, String sender, String receiver){
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	/* Setters */
	public void setContent(String _content){
		this.content = _content;
	}
	
	public void setSender(String _sender){
		this.sender =  _sender;
	}
	
	public void setReceiver(String _receiver){
		this.receiver = _receiver;
	}
	
	/* Getters */
	public String getContent(){
		return this.content;
	}
	
	public String getSender(){
		return this.sender;
	}
	
	public String getReceiver(){
		return this.receiver;
	}
}
