package entities;

public class Contact {
	
	private String name;
	private String group;
	private double latitude;
	private double longitude;
	private String message;
	private boolean share;
	
	/* Constructor */
	public Contact(String name, String group, double latitude, double longitude, 
			String message, boolean share){
		this.name = name;
		this.group = group;
		this.latitude = latitude;
		this.longitude = longitude;
		this.message = message;
		this.share = share;
	}
	
	/* Setters */
	public void setName(String _name){
		this.name = _name;
	}
	
	public void setGroup(String _group){
		this.group = _group;
	}
	
	public void setLatitude(double _latitude){
		this.latitude = _latitude;
	}
	
	public void setLongitude(double _longitude){
		this.longitude = _longitude;
	}
	
	public void setMessage(String _message){
		this.message = _message;
	}
	
	public void setShare(boolean _share){
		this.share = _share;
	}
	
	/* Getters */
	public String getName(){
		return this.name;
	}
	
	public String getGroup(){
		return this.group;
	}
	
	public double getLatitude(){
		return this.latitude;
	}
	
	public double getLongitude(){
		return this.longitude;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public boolean getShare(){
		return this.share;
	}
	
}
