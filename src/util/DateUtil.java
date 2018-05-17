package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date StringToDate2(String sDate){
		try {
			Date date = d.parse(sDate);
			
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	public static Date StringToDate(String sDate){
		try {
			Date date = df.parse(sDate);
			
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	

	public static String DateToString(Date date){
		String sDate = df.format(date);
		return sDate;		
	}
	
	
	public static void main(String[] args){
		
		String sdate = "2018-05-10 11:17:06";
		System.out.println(StringToDate(sdate));
	}
}
