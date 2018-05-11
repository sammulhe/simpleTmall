package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojo.OrderItem;
import pojo.Product;
import util.DBUtil;

public class OrderItemDao {
	
	//不需要分页
	public List<OrderItem> list(int oid){
		List<OrderItem> orderItems = new ArrayList<>();
		String sql = "select * from orderItem o left join product p on o.pid = p.id where o.oid = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, oid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt(1));
				orderItem.setPid(rs.getInt(2));
				orderItem.setOid(rs.getInt(3));
				orderItem.setUid(rs.getInt(4));
				orderItem.setNumber(rs.getInt(5));
				Product product = new Product();
				product.setId(rs.getInt(6));
				product.setName(rs.getString(7));
				product.setSubTitle(rs.getString(8));
				product.setOriginalPrice(rs.getFloat(9));
				product.setPromotePrice(rs.getFloat(10));
				orderItem.setProduct(product);
				
				orderItems.add(orderItem);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderItems;
 	}
	

}
