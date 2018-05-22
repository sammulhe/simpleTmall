package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.Administrator;
import util.DBUtil;

public class AdministratorDao {

	//用于用户登录，检查账号密码是否正确
	public Administrator find(String name, String password){
		Administrator admin = null;
		String sql = "select * from administrator where name = ?";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(rs.getString(3).equals(password)){
					admin= new Administrator();
					admin.setId(rs.getInt(1));
					admin.setName(rs.getString(2));
					admin.setPassword(rs.getString(3));
					
					connection.close(); //在return前必须要先close connection，不然以后只能对数据库进行查询功能
					return admin;
				}
			}
			
			connection.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return admin;
	}
}
