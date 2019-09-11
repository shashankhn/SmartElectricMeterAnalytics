package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class area {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection con;
		Statement stt=null;
		Statement stt2 =null;
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String pwd = "";
		String query;
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url,user,pwd);
		
		stt = con.createStatement();
		stt2 = con.createStatement();
		stt.execute("USE project");
		stt2.execute("USE project");
//		query ="SELECT pincode FROM userinfo GROUP BY pincode";
		
		ResultSet rs = stt.executeQuery("SELECT pincode FROM userinfo GROUP BY pincode");
//		ResultSet rs2 = null;
		int rowcount = 0;
		int count = 0;
		// to know how many unique pincode exist
//		if (rs.last()) {
//		  rowcount = rs.getRow();
//		  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
//		}
		if(!rs.next())
		{	System.out.println("hii");
			rowcount++;
		}
		System.out.println(rowcount);
		 int[] pincode = new int[rowcount];
		 // to store all unique pincode
		while(rs.next())
		{
			int curpincode = rs.getInt("pincode");
			pincode[count++] = curpincode;
			
		}
		System.out.println(count);
		
		while(count>0)
		{
			count--;
			// select the group of meter_id in a particular pincode
			query = "SELECT meter_id FROM userinfo WHERE pincode ="+pincode[count]+"";
			rs = stt.executeQuery(query);
			double particularAreaUnit = 0;
			while(rs.next())
			{ 
				
				// fetch particular meter_id
				int meter_id = rs.getInt("meter_id");
				// using that meter_id get totalunits consumed in a day 
				query = "SELECT unitsconsumed FROM peakntotal where meter_id ="+meter_id+"";
//				rs2 = stt2.executeQuery(query);
//				rs2.next();
//				double unitsconsumed = rs2.getDouble("unitsconsumed");
//				particularAreaUnit += unitsconsumed;
				
			}
		System.out.println(pincode[count] + "   "+ particularAreaUnit);
		
		}
		
		// get to know how many pin code 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
}
}
