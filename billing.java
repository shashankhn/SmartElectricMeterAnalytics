package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class billing {
	
//	public  static int meterid = 10001;

	public static void main(String[] args) {
	
			Connection con;
			Statement stt=null;
			Statement stt2 = null;
			String url = "jdbc:mysql://localhost:3306/";
			String user = "root";
			String pwd = "";
			//System.out.println("hello");
			try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url,user,pwd);
			
			stt = con.createStatement();
			stt2 = con.createStatement();
			
			stt.execute("USE project");
			stt2.execute("USE project");
			String query = "SELECT * FROM tariff";
			ResultSet rs = stt.executeQuery(query);
			rs.next();
			double n30 = rs.getDouble("normal30");
			double n70 = rs.getDouble("normal70");
			double n100 = rs.getDouble("normal100");
			double aboven100 = rs.getDouble("aboven100");
			double p30 = rs.getDouble("peak30");
			double p70 = rs.getDouble("peak70");
			double p100 = rs.getDouble("peak100");
			double abovep100 = rs.getDouble("abovep100");
			
			System.out.println(n30);
			query = "SELECT * FROM billing";
			rs = stt.executeQuery(query);
			while(rs.next())
			{	
				int meter_id = rs.getInt("meter_id");
				double total = rs.getDouble("total");
				double totalpeak = rs.getDouble("totalpeak");
				double cost = 0;
				double totalnormal = total -  totalpeak;
				if(totalnormal>=100)
				{
					cost = cost + aboven100*(totalnormal-100);
					totalnormal = 100;
				}
				if(totalnormal >= 70)
				{
					cost = cost + n100*(totalnormal - 70);
					totalnormal = 70;
				}
				if(totalnormal >= 30)
				{
					cost = cost + n70*(totalnormal - 30);
					totalnormal = 30;
				}
				if(totalnormal >= 0)
				{
					cost = cost + n30*(totalnormal);
				}
				
				if(totalpeak>=100)
				{
					cost = cost + abovep100*(totalpeak-100);
					totalpeak = 100;
				}
				if(totalpeak >= 70)
				{
					cost = cost + p100*(totalpeak - 70);
					totalpeak = 70;
				}
				if(totalpeak >= 30)
				{
					cost = cost + p70*(totalpeak - 30);
					totalpeak = 30;
				}
				if(totalpeak >= 0)
				{
					cost = cost + p30*(totalpeak);
				}
				System.out.println(cost);
				query = "UPDATE billing set cost ="+cost+" where meter_id = "+meter_id+"";
				stt2.executeUpdate(query);
			}
			
				
			}
			
			catch(Exception e) {
				e.printStackTrace();
			}
		}

}
