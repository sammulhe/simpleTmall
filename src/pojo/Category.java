package pojo;

import java.util.List;

public class Category {
	
	private int id;
	private String name;
	private List<Product> products;
	
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
	
	public List<Product> getProducts(){
		return this.products;
	}
	public void setProducts(List<Product> products){
		this.products = products;
	}
}
