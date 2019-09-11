package database;

import java.sql.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.*;
import static database.multithreading.meterid;
import static database.multithreading.meteridmax;


public class multithreading {
	public  static int meterid = 10000;
	public  static int meteridmax = 10000;
	public static void main(String[] args) {
		Connection con;
		Statement st=null;
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String pwd = "";
		String query = "";
		int count = 0;
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url,user,pwd);
		
		st = con.createStatement();
		st.execute("USE project");
		query = "SELECT  COUNT(*) FROM login";
		ResultSet r = st.executeQuery(query);
		r.next();
		count = r.getInt(1);
		meteridmax = meteridmax + count;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(meterid);
		System.out.println(meteridmax);
		System.out.println(count);
		peakhour ph1 = new peakhour();
		peakhour ph2 = new peakhour();
		ph1.start();
		ph2.start();
		
	}
	 
}
class peakhour extends Thread {
	
	public void run()
	{	Connection con;
		Statement stt=null;
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String pwd = "";
		String prevDate = getPrevDate();
		System.out.println(prevDate);
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url,user,pwd);
		
		stt = con.createStatement();
		
		stt.execute("USE project");
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		while(meterid<meteridmax)
		{
		double units=0;
		int hour=1;
		int id = meterid;
		String filename = Integer.toString(id);
		
		String path = "C:" + File.separator + "xampp" + File.separator + filename+".tsv";
		FileOutputStream fos = null;
		File f;
		String mycontent = "date"+"\t"+"value"+System.getProperty("line.separator");
		try {
			f = new File(path);
			fos = new FileOutputStream(f);

			if (!f.exists()) {
				f.getParentFile().mkdirs();

				f.createNewFile();

			}
			byte[] bytesArray = mycontent.getBytes();

			  fos.write(bytesArray);
			  fos.flush();
			  System.out.println("File Written Successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				fos.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		double totalunit =0;
		int timeid = 0;
		double pk =0;
		meterid++;
		String query = "SELECT * FROM hourdata WHERE meter_id ="+id+"";
		try{
		ResultSet rs = stt.executeQuery(query);
		rs.next();
		//while(rs.next())
		//{	
		
		byte[] b1 ;
			for(int i=0;i<=1380;i++)
		{	
			double cur=0;
			String str2= Integer.toString(hour)+"\t";
			String str = "H"+i+"";
			cur = rs.getDouble(str);
			units = units + cur;
			if(i%60==0)
			{
				totalunit = totalunit + cur;
				str2+= Double.toString(cur)+System.getProperty("line.separator");
				byte[] b = str2.getBytes();
				fos.write(b);
				hour++;
				
				
			
			}
			if(cur>pk)
			{
				pk = cur;
				timeid = i;
			}
		}
		}
			
//		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		int peakhour = timeid/60 ;
		int peakmin = timeid%60;
		System.out.println(id+" "+units+"perday units ="+totalunit);
	//	String date = "2017-04-20";
	//	query = "INSERT INTO peakntotal (date,peakunit,unitsconsumed) values ("+date+","+pk+","+totalunit+")where meter_id="+id+"";
		
		query = "UPDATE peakntotal SET date = "+prevDate+",peakunit="+pk+",peakhour="+peakhour+",peakmin="+peakmin+",unitsconsumed="+totalunit+" where meter_id="+id+"";
		String querybill = "SELECT * FROM billing WHERE meter_id = "+id+"";// AND month = "+4+"";
		
		try {
			stt.executeUpdate(query);
			stt.executeUpdate(querybill);
			ResultSet rs = stt.executeQuery(querybill);
			rs.next();
			double total = rs.getDouble("total");
			total = total + totalunit;
			double peaktotal = rs.getDouble("totalpeak");
			peaktotal = peaktotal + pk;
		String updatebill = "UPDATE billing SET total = "+total+",totalpeak = "+peaktotal+" WHERE meter_id ="+id+" AND month = "+4+"";
			stt.executeUpdate(updatebill);
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("done");
		
		}
	}	

	
	public String getPrevDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		 final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -1);
		    return dateFormat.format(cal.getTime());
	}

}		
	
	
	
	

