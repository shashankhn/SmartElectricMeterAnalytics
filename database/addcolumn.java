package database;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.sql.*;

public class addcolumn {

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
		
	//	stt.execute("INSERT INTO hourdata (1,2,3,4) VALUES ('11','12','13','14')" );
		
	//	String insert = "CREATE TABLE REGISTRATION " +
    //            "(id INTEGER not NULL, " +
    //            " first VARCHAR(255), " + 
    //            " last VARCHAR(255), " + 
    //            " age INTEGER, " + 
    //            " PRIMARY KEY ( id ))"; 
		String insert = "INSERT INTO hourdata (meter_id) VALUES (10000)";
		stt.execute(insert);
/*		for(int i=1; i<=1380; i++)
		{	
			// int i = 1017;
			String num = Integer.toString(i);
			String str = "ALTER TABLE hourdata ADD H"+ num ;
			str = str + " double";
			
			System.out.println(str);
		stt.executeUpdate(str);
		}
*/		System.out.print("done");
		
	//	stt.execute("ALTER TABLE hourdata DROP COLUMN H9" );
		
	//	stt.execute(insert);
		stt.close();
		con.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
