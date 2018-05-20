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
	
	public List<OrderItem> foreList(int oid){
		List<OrderItem> orderItems = new ArrayList<>();
		String sql = "select * from orderItem where oid = ?";
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
				
				orderItems.add(orderItem);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderItems;
	}
	
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
	
	public OrderItem getOne(int id){
		OrderItem orderItem = new OrderItem();
		String sql = "select * from orderItem where id = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				orderItem.setId(rs.getInt(1));
				orderItem.setPid(rs.getInt(2));
				orderItem.setOid(rs.getInt(3));
				orderItem.setUid(rs.getInt(4));
				orderItem.setNumber(rs.getInt(5));
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderItem;
	}
	
	public void update(OrderItem orderItem){
		String sql = "update orderItem set number = ? where id = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderItem.getNumber());
			ps.setInt(2, orderItem.getId());
			ps.executeUpdate();

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id){
		String sql = "delete from orderItem where id = ?";
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
	
	
	public int getProductSaleCount(int pid){
		int total = 0;
		String sql = "select number from orderItem where pid = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, pid);			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				total = total + rs.getInt(1);
			}

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return total;
	}
	
	public void add(OrderItem orderItem){
		//如果数据库中存在可以合并的订单，直接就返回,在check的时候已经合并了
		if(this.checkExit(orderItem) == true){
			return;
		}
		String sql = "insert into orderItem (pid,uid,number) values (?,?,?)";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderItem.getPid());
			ps.setInt(2, orderItem.getUid());
			ps.setInt(3, orderItem.getNumber());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			
			while(rs.next()){
				orderItem.setId(rs.getInt(1));
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//在添加新的订单项的时候，先看看是否是存在订单项可以合并，uid，oid，pid都相同即认为可以合并，就是number数相加
	public boolean checkExit(OrderItem orderItem){
		String sql = "select * from orderItem where oid is null and uid = ? and pid = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderItem.getUid());
			ps.setInt(2, orderItem.getPid());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){	
				orderItem.setId(rs.getInt(1));
				orderItem.setNumber(orderItem.getNumber() + rs.getInt(5));
				connection.close();  //close要在update前，setnumber后执行，不然close以后，就没有任何数据，update前必须close，不然数据库会锁定
				update(orderItem);
				return true;
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	//获取用户购物车的订单
	public List<OrderItem> getCartOrderItems(int uid){
		List<OrderItem> orderItems = new ArrayList<>();
		String sql = "select * from orderItem where oid is null and uid = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt(1));
				orderItem.setPid(rs.getInt(2));
				orderItem.setUid(rs.getInt(4));
				orderItem.setNumber(rs.getInt(5));
				orderItem.setOid(rs.getInt(3));
			
				orderItems.add(orderItem);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderItems;
	}

	//获取用户购物车的总数
	public int getCartTotal(int uid){
		int total = 0;
		String sql = "select * from orderItem where oid is null and uid = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				total = total + rs.getInt(5);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return total;
	}
	
	//生成订单的order id，将它赋值到订单项中的oid
	public void updateOid(OrderItem orderItem){
		String sql = "update orderItem set oid = ? where id = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderItem.getOid());
			ps.setInt(2, orderItem.getId());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
