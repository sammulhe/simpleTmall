package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.Order;
import pojo.OrderItem;
import pojo.User;
import util.DBUtil;

public class OrderDao {

	public List<Order> list(int start, int count){
		List<Order> orders = new ArrayList<>();
		//order在sqlite有特殊意义，所以要有'' 区分表和关键字
		String sql = "select * from 'order' o left join user u on o.uid = u.id "
				+ " order by o.id DESC limit ?,?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Order order = new Order();
				order.setId(rs.getInt(1));
				order.setOrderCode(rs.getString(2));
				order.setAddress(rs.getString(3));
				order.setPost(rs.getString(4));
				order.setReceiver(rs.getString(5));
				order.setMobile(rs.getString(6));
				order.setUserMessage(rs.getString(7));
				order.setCreateDate(rs.getString(8));
				order.setPayDate(rs.getString(9));
				order.setDeliveryDate(rs.getString(10));
				order.setConfirmDate(rs.getString(11));
				order.setStatus(rs.getString(12));
				User user = new User();
				user.setId(rs.getInt(14));
				user.setUsername(rs.getString(15));
				order.setUser(user);
				
				orders.add(order);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orders;
	}
	
	
	public int getTotal(){
		int total = 0;
		String sql = "select count(*) from 'order'";
		try {
			Connection connection = DBUtil.getConnection();
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			total = rs.getInt(1);
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return total;
	}
	
	//更新
	public void update(Order order){
		String sql = "update 'order' set status = ?,createDate=?,payDate=?,deliveryDate = ?,confirmDate=? where id = ?";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, order.getStatus());
			ps.setString(2, order.getCreateDate());
			ps.setString(3, order.getPayDate());
			ps.setString(4, order.getDeliveryDate());
			ps.setString(5, order.getConfirmDate());
			ps.setInt(6, order.getId());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//插入一个订单
	public void add(Order order){
		String sql = "insert into 'order' (orderCode,address,post,receiver,mobile,userMessage,createDate,status,uid)"
				+ "values (?,?,?,?,?,?,?,?,?)";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, order.getOrderCode());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getPost());
			ps.setString(4, order.getReceiver());
			ps.setString(5, order.getMobile());
			ps.setString(6, order.getUserMessage());
			ps.setString(7, order.getCreateDate());
			ps.setString(8, order.getStatus());
			ps.setInt(9, order.getUid());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			
			while(rs.next()){
				order.setId(rs.getInt(1));
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public Order getOne(int id){
		Order order = new Order();
		String sql = "select * from 'order' where id = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				order.setId(rs.getInt(1));
				order.setOrderCode(rs.getString(2));
				order.setAddress(rs.getString(3));
				order.setPost(rs.getString(4));
				order.setReceiver(rs.getString(5));
				order.setMobile(rs.getString(6));
				order.setUserMessage(rs.getString(7));
				order.setCreateDate(rs.getString(8));
				order.setPayDate(rs.getString(9));
				order.setDeliveryDate(rs.getString(10));
				order.setConfirmDate(rs.getString(11));
				order.setStatus(rs.getString(12));
				order.setUid(rs.getInt(13));
				
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return order;		
	}
	
}
