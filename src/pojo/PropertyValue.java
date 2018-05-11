package pojo;

public class PropertyValue {

	private int id;
	private int pid;
	private int ptid;
	private String value;
	private Property property;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public int getPid(){
		return this.pid;
	}
	public void setPid(int pid){
		this.pid = pid;
	}
	
	public int getPtid(){
		return this.ptid;
	}
	public void setPtid(int ptid){
		this.ptid = ptid;
	}
	
	public String getValue(){
		return this.value;
	}
	public void setValue(String value){
		this.value = value;
	}
	
	public Property getProperty(){
		return this.property;
	}
	public void setProperty(Property property){
		this.property = property;
	}
}
