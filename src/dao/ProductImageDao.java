package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojo.ProductImage;
import util.DBUtil;

public class ProductImageDao {

	public List<ProductImage> listByType(int pid,String type){
		List<ProductImage> productImages = new ArrayList<>();
		String sql = "select * from product_image where pid = ? and type=?";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, pid);
			ps.setString(2, type);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				ProductImage productImage = new ProductImage();
				productImage.setId(rs.getInt(1));
				productImage.setPid(rs.getInt(2));
				productImage.setType(rs.getString(3));
				
				productImages.add(productImage);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productImages;
	}
	
	public ProductImage getOne(int id){
		ProductImage productImage = new ProductImage();
		String sql = "select * from product_image where id = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				productImage.setId(rs.getInt(1));
				productImage.setPid(rs.getInt(2));
				productImage.setType(rs.getString(3));
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productImage;
	}
	
	public void add(ProductImage productImage){
		String sql = "insert into product_image (pid,type) values (?,?)";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, productImage.getPid());
			ps.setString(2, productImage.getType());
			ps.execute();			
			ResultSet rs = ps.getGeneratedKeys();
			
			while(rs.next()){
				productImage.setId(rs.getInt(1));
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id){
		String sql = "delete from product_image where id = ?";
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
}
