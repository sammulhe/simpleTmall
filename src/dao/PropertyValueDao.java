package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojo.Property;
import pojo.PropertyValue;
import util.DBUtil;

public class PropertyValueDao {
	
	public List<PropertyValue> list(int pid){
		List<PropertyValue> propertyValues = new ArrayList<>();
		String sql = "select * from property_value pv left join property p on pv.ptid = p.id where pv.pid = ?";
		
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				PropertyValue propertyValue = new PropertyValue();
				
				propertyValue.setId(rs.getInt(1));
				propertyValue.setPtid(rs.getInt(2));
				propertyValue.setPid(rs.getInt(3));
				propertyValue.setValue(rs.getString(4));
				Property property = new Property(); //属性名称
				property.setId(rs.getInt(5));
				property.setCid(rs.getInt(6));
				property.setName(rs.getString(7));
				propertyValue.setProperty(property); //在属性值中加入对应的属性名称
				
				propertyValues.add(propertyValue);
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return propertyValues;
	}
	
	public void update(PropertyValue propertyValue){
		String sql = "update property_value set value = ? where id = ?";
		try {
			Connection connection = DBUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, propertyValue.getValue());
			ps.setInt(2, propertyValue.getId());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
