package pojo;

import java.util.List;

public class Category {
	
	private int id;
	private String name;
	private List<Product> products;
	private List<List<Product>> productsByRow; //首页每个分类对应不同的推荐产品集合
	
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
	
	public List<List<Product>> getProductsByRow(){
		return this.productsByRow;
	}
	public void setProductsByRow(List<List<Product>> productsByRow){
		this.productsByRow = productsByRow;
	}
}
