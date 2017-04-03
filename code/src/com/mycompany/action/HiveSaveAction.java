package com.mycompany.action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class HiveSaveAction {
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public String execute() {
		try
		{
			ServletContext context = ServletActionContext.getServletContext();
			String fpath = context.getRealPath("") + "/" + "config.properties";
			FileInputStream fileInputStream=new FileInputStream(new File(fpath));
			Properties properties=new Properties();
			properties.load(fileInputStream);
			Class.forName(driverName);
			Connection con = DriverManager.getConnection(
					"jdbc:hive://localhost:10000/dataminingbigdata", "", "");
			Statement stmt = con.createStatement();
			Configuration conf = new Configuration();
			conf.addResource(new Path(
					properties.getProperty("hadoopLoc")));
			FileSystem fs = FileSystem.get(conf);
			Path pt = new Path(properties.getProperty("hdfsLoc"));
			
			FileStatus[] status = fs.listStatus(pt);
			// fs.delete(pt, true);
			for (int i = 0; i < status.length; i++) {
				String filePath = status[i].getPath().toString();
				Path path=new Path(filePath);
				System.out.println("calling insert query !!");
				String sql = "load data inpath '" + "/result/"+path.getName() + "' OVERWRITE into table "
						+ "twitter_table";
				stmt.executeQuery(sql);
			}
			fileInputStream.close();
			stmt.close();
			con.close();
			fs.close();
			System.out.println("Inserted Sucessfully !!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return ActionSupport.SUCCESS;
	}
}
