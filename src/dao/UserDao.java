package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pojo.User;
import util.DBUtil;

//User相关的操作
public class UserDao {
	
	
	public List<User> list(){
		int total = this.getTotal();
		List<User> users = list(0,total);
		return users;
	}
	
	//分页查询
	public List<User> list(int start, int count){
		List<User> users = new ArrayList<>();
		String sql = "select * from user limit ?,?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				
				users.add(user);
			}
			
			connection.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}

	//获取总数
	public int getTotal(){
		int total = 0;
		String sql = "select count(*) from user";
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
	
	
	public void add(User user){
		String sql = "insert into user (username,password) values (?,?)";
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.execute();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//用于检查用户名是否存在
	public boolean check(String username){
		String sql = "select * from user where username = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				connection.close();
				return true;
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	//用于用户登录，检查账号密码是否正确
	public User find(String username, String password){
		User user = null;
		String sql = "select * from user where username = ?";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(rs.getString(3).equals(password)){
					user = new User();
					user.setId(rs.getInt(1));
					user.setUsername(rs.getString(2));
					user.setPassword(rs.getString(3));
					
					connection.close(); //在return前必须要先close connection，不然以后只能对数据库进行查询功能
					return user;
				}
			}
			
			connection.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
}
