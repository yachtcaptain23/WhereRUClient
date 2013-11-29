package entities;

public class Group {
	
	private String group_name;
	
	/* Constructor */
	public Group(String group_name){
		this.group_name = group_name;
	}
	
	/* Setters */
	public void setGroupName(String _group_name){
		this.group_name = _group_name;
	}
	
	/* Getters */
	public String getGroupName(){
		return this.group_name;
	}
	
}
