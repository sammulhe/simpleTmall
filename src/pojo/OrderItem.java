package pojo;

public class OrderItem {
	private int id;
	private int pid;
	private int oid;
	private int uid;
	private int number;
	
	private Product product;  //获取product，需要获得单价，以及计算总价
	
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
	
	public int getOid(){
		return this.oid;
	}
	public void setOid(int oid){
		this.oid = oid;
	}
	
	public int getUid(){
		return this.uid;
	}
	public void setUid(int uid){
		this.uid = uid;
	}
	
	public int getNumber(){
		return this.number;
	}
	public void setNumber(int number){
		this.number = number;
	}
	
	public Product getProduct(){
		return this.product;
	}
	public void setProduct(Product product){
		this.product = product;
	}
}
