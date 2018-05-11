package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Category;
import pojo.Product;
import util.DBUtil;

public class CategoryDao {
    
    //分页获取Category
    public List<Category> list(int start, int count){
    	List<Category> categorys = new ArrayList<>(); //必须初始化，不然是空指针
    	String sql = "select * from category limit ?,?";     //获取	

    	
		try {
			Connection connection = DBUtil.getConnection(); //必须是静态才能这样，不然要进行初始化
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1, start);
	    	s.setInt(2, count);  	
	    	ResultSet rs = s.executeQuery();
	    	
	    	while(rs.next()){
	    		Category c = new Category();
	    		c.setId(rs.getInt(1));
	    		c.setName(rs.getString(2));
	    		
	            categorys.add(c);
	    	}
	    	
	    	connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    	return categorys;
    }
    
    
    //获取总数
    public int getTotal(){
    	String sql = "select count(*) from category";
    	int total = 0;
    	
		try {
	    	Connection connection = DBUtil.getConnection();
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
	    	total = rs.getInt(1);
	    	
	    	connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return total;
    }
    
    
    //根据id获得一个Category
    public Category getOne(int id){
    	Category category = new Category();
    	List<Product> products = new ArrayList<>();
    	String sql = "select * from category c left join product p on c.id = p.cid where c.id = ?";
    	
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			category.setId(rs.getInt(1));
			category.setName(rs.getString(2));
			while(rs.next()){
			
			Product product = new Product();
			product.setId(rs.getInt(3));
			product.setName(rs.getString(4));
			product.setSubTitle(rs.getString(5));
			product.setOriginalPrice(rs.getFloat(6));
			product.setPromotePrice(rs.getFloat(7));
			product.setStock(rs.getInt(8));
			product.setCid(rs.getInt(10));
			
			products.add(product);
			}
			category.setProducts(products);
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return category;
    }
    
    
    //更新一个Category
    public void update(Category category){
    	String sql = "update category set name=? where id=? ";
    	
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, category.getName());
			ps.setInt(2, category.getId());
            ps.executeUpdate();
            
            connection.close();
            
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    //删除一个Category
    public void delete(int id){
    	String sql = " delete from category where id= ?";
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //增加一个Category
    public void add(Category category){
    	String sql = "insert into category (name) values (?)";
    	
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, category.getName());			
			ps.execute();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}
