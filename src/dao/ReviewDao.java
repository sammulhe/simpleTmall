package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojo.Review;
import util.DBUtil;
import util.DateUtil;

public class ReviewDao {
	
	public List<Review> list(int pid){
		int total = this.getTotal(pid);
		List<Review> reviews = list(pid,0,total);
		
		return reviews;
	}
	
	//分页查询
	public List<Review> list(int pid, int start, int count){
		List<Review> reviews = new ArrayList<>();		
		String sql = "select * from review where pid = ? limit ?,?";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Review review = new Review();
				review.setId(rs.getInt(1));
				review.setContent(rs.getString(2));
				review.setCreateDate(DateUtil.StringToDate2(rs.getString(3)));
				review.setUid(rs.getInt(4));
				review.setPid(rs.getInt(5));
				
				reviews.add(review);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reviews;
	}

	public int getTotal(int pid){
		int total = 0;
		String sql = "select count(*) from review where pid = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				total = rs.getInt(1);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return total;
	}
	
	public void add(Review review){
		String sql = "insert into review (content,createDate,uid,pid) values (?,?,?,?)";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, review.getContent());
			ps.setString(2, DateUtil.DateToString(review.getCreateDate()));
			ps.setInt(3, review.getUid());
			ps.setInt(4, review.getPid());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
