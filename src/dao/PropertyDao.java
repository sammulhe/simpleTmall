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
import pojo.Property;
import util.DBUtil;

public class PropertyDao {
	
    
  //分页获取Property
    public List<Property> list(int cid, int start, int count){
    	List<Property> properties = new ArrayList<>(); //必须初始化，不然是空指针
    	String sql = "select * from property where cid = ? limit ?,?";     //获取	

		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1, cid);
			s.setInt(2, start);
	    	s.setInt(3, count);  	
	    	ResultSet rs = s.executeQuery();
	    	
	    	while(rs.next()){
	    		Property property = new Property();
	    		property.setId(rs.getInt(1));
	    		property.setCid(rs.getInt(2));
	    		property.setName(rs.getString(3));
	    		
	            properties.add(property);
	    	}
	    	
	    	connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    	return properties;
    }
    
    
    //获取总数
    public int getTotal(int cid){
    	String sql = "select count(*) from property where cid = ?";
    	int total = 0;
    	
		try {
	    	Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, cid);
			ResultSet rs = ps.executeQuery();
	    	total = rs.getInt(1);
	    	
	    	connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return total;
    }

    //获取一个Property,同时获得property对应的category
    public Property getOne(int id){
    	Property property = new Property();
    	String sql = "select * from property p left join category c on p.cid = c.id where p.id = ?";
    	
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			property.setId(rs.getInt(1));
			property.setCid(rs.getInt(2));
			property.setName(rs.getString(3));
			Category category = new Category();
			category.setId(rs.getInt(4));
			category.setName(rs.getString(5));
			property.setCategory(category);
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return property;
    }
    
   //更新一个Property
    public void update(Property property){
    	String sql = "update property set name=? where id=?  ";
    	
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, property.getName());
			ps.setInt(2, property.getId());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //删除一个property
    public void delete(int id){
    	String sql = "delete from property where id = ?";
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
    
    //增加一个Property
    public void add(Property property){
    	String sql = "insert into property (cid,name) values (?,?) ";
    	try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, property.getCid());
			ps.setString(2, property.getName());
			ps.execute();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
