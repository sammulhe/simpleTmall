package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String Class_Name = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:D:\\download\\Job\\tmall_ssm.db";

    static{
        try {
            Class.forName(Class_Name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL);
	
    }
    
    public static void main(String[] args) throws SQLException{
    	System.out.println(getConnection());
    }

}
