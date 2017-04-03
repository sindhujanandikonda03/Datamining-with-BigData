package com.mycompany.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveClientCall {
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	private JsonConverter jsonConverter=new JsonConverter();
	public void clientCall(String FilePath) {
		try {
			Class.forName(driverName);
			Connection con = DriverManager.getConnection( 
					"jdbc:hive://localhost:10000/dataminingbigdata", "", "");
			Statement stmt = con.createStatement();
			String sql = "select * from twitter_table";
			ResultSet res=stmt.executeQuery(sql);
			String content="";
			while(res.next())
			{
				String row=res.getString(1);
				if(row.contains("since_id_str"))
				{
					//FileOutputStream fileOutputStream=new FileOutputStream(new File("result.json"));;
					content+=row;
					System.out.println(content);
					FileOutputStream fileOutputStream=new FileOutputStream(new File(FilePath+"/result.json"));;
					fileOutputStream.write(content.getBytes());
					fileOutputStream.close();
					content="";
				}
				else
				{
					content+=row;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		HiveClientCall hiveClientCall = new HiveClientCall();
		hiveClientCall.clientCall("");
	}
}
