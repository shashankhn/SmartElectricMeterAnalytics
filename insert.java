package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insert {

	public static void main(String args[]){

		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String pwd = "";
		System.out.println("hello");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(url,user,pwd);
			Statement stt = con.createStatement();
			stt.execute("USE project");

			String sql;
			// for all meter_id for particular  one we need to remove loop and assign meter_id 
			for(int id=10015;id<=10015;id++)
			{
				// for 1st hour off the day;
				double[] hr = new double[60];
				double units = 0;
				String index = "0";
				double result = 0;
				insert obj=new insert();
				for(int i=0;i<60;i++)
				{
					hr[i] = Math.random()/50;
					units = units + hr[i];
					// 6000 millisecond of sleep that is equal to 1 minute 		
					//				sleep(6000);
				}
				// Updating sum of the units consumed in first one hour off the day	
				sql ="UPDATE hourdata SET H"+index+" ="+units+" WHERE meter_id ="+id+"";
				stt.executeUpdate(sql);
				obj.hourlyDataClient(id,"H"+index,units);
				System.out.println(units);
				// applying window sliding concept	
				for(int i=1;i<=1380;i++)
				{
					// Converting index to string
					index = Integer.toString(i);
					int windowindex = (i-1)%60;
					// minute unit consumed
					double minute = Math.random()*Math.random()/50;
					if(i%3 == 0)
						minute = minute*2;
					if(i%60==0)
						result =result + units;
					units = units - hr[windowindex] + minute;
					hr[windowindex] = minute;
					sql ="UPDATE hourdata SET H"+index+" ="+units+" WHERE meter_id ="+id+"";
					stt.executeUpdate(sql);
					System.out.println(units);
					obj.hourlyDataClient(id,"H"+index,units);
				}
				System.out.println(result);
			}

			stt.close();
			con.close();

		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void hourlyDataClient(int id,String columnName,double columnValue)
	{
		try {


			URL url = new URL("http://192.168.43.188:8080/HourlyData");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");

			String input = "{\"meterId\":\""+id+"\",\"columnName\":\""+columnName+"\",\"columnValue\":\""+columnValue+"\"}";
			System.out.println(input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
			//System.exit(0);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sleep(int i) {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(i);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
