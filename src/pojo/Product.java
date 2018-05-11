package pojo;

public class Product {
	private int id;
	private String name;
	private String subTitle;
	private float originalPrice;
	private float promotePrice;
	private int stock;
	private String createDate;
	private int cid;
	private Category category;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getSubTitle(){
		return this.subTitle;
	}
	public void setSubTitle(String subTitle){
		this.subTitle = subTitle;
	}
	
	public float getOriginalPrice(){
		return this.originalPrice;
	}
	public void setOriginalPrice(float originalPrice){
		this.originalPrice = originalPrice;
	}
	
	public float getPromotePrice(){
		return this.promotePrice;
	}
	public void setPromotePrice(float promotePrice){
		this.promotePrice = promotePrice;
	}
	
	public int getStock(){
		return this.stock;
	}
	public void setStock(int stock){
		this.stock = stock;
	}
	
	public String getCreateDate(){
		return this.createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}
	
	public int getCid(){
		return this.cid;
	}
	public void setCid(int cid){
		this.cid = cid;
	}
	
	public Category getCategory(){
		return this.category;
	}
	public void setCategory(Category category){
		this.category = category;
	}
}
