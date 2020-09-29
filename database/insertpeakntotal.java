package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insertpeakntotal {

	public static void main(String[] args) {	
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String pwd = "";
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection(url,user,pwd);
		
		Statement stt = con.createStatement();
		
		stt.execute("USE project");
		String sql ; 
		for(int i = 10000 ; i<=10005;i++)
		{
			int id = i;
			String password = Integer.toString(i);
			String sql1 = "INSERT INTO login (meter_id,password) VALUES ("+id+","+password+")";	
			stt.execute(sql1);
			
		}
		stt.close();
		con.close();

	}
		catch(Exception e) {
			e.printStackTrace();
		}

}
}
