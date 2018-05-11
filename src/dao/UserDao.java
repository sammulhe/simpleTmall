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
}
