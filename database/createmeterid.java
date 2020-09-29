package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createmeterid {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String pwd = "";
		//System.out.println("hello");
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection(url,user,pwd);
		
		Statement stt = con.createStatement();
		
		stt.execute("USE project");
		
		String values,sql;
		int  insert=0;
		for (int i= 10001;i<=10050;i++)
		{
		int id = i;
		sql = "INSERT INTO peakntotal (meter_id) VALUES ("+id+")";
		stt.execute(sql);
		}
		double result =0;
	/*	
		for(int i=1;i<=1440;i++)
		{String ii = Integer.toString(i);
			double units = Math.random();
			result =result + units;
		//	sql = "INSERT INTO minValues (H"+ii+") VALUES ("+units+") WHERE meter_id = "+id+"";
			// update table name set fileds where meter id condition
		//	sql ="UPDATE minValues SET H"+ii+" ="+units+" WHERE meter_id ="+id+"";
			stt.executeUpdate(sql);
		}
		*/
		System.out.println("done "+result);
		stt.close();
		con.close();
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
