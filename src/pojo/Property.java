package pojo;

public class Property {

	private int id;
	private int cid;
	private String name;
	private Category category;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public int getCid(){
		return this.cid;
	}
	public void setCid(int cid){
		this.cid = cid;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public Category getCategory(){
		return this.category;
	}
	public void setCategory(Category category){
		this.category = category;
	}
}
