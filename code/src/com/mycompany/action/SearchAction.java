package com.mycompany.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.struts2.ServletActionContext;

import com.mycompany.logic.TwitterRestCall;
import com.mycompany.vo.SearchVo;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction {
	private List<SearchVo> searchDetails;
	private String search;
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<SearchVo> getSearchDetails() {
		return searchDetails;
	}

	public void setSearchDetails(List<SearchVo> searchDetails) {
		this.searchDetails = searchDetails;
	}

	private TwitterRestCall twitterRestCall;

	public String execute() {
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath("") + "/" + "config.properties";
		twitterRestCall = new TwitterRestCall(filePath);
		searchDetails = twitterRestCall.getTweetSearch(filePath, search);
		/*try {
			filePath = context.getRealPath("") + "/" + "config.properties";
			FileInputStream fileInputStream=new FileInputStream(new File(filePath));
			Properties properties=new Properties();
			properties.load(fileInputStream);
			
			Configuration conf = new Configuration();
			conf.addResource(new Path(
					properties.getProperty("hadoopLoc")));
			FileSystem fs = FileSystem.get(conf);
			Path pt = new Path(properties.getProperty("hdfsLoc"));

			FileStatus[] status = fs.listStatus(pt);
			//fs.delete(pt, true);
			if(status !=null)
			{
				for (int i = 0; i < status.length; i++) {
					fs.delete(status[i].getPath(), true);
				}
				fileInputStream.close();
				fs.close();
			}
			filePath = context.getRealPath("") + "/result";
			File file = new File(filePath);
			String[] file_list = file.list();
			for (String fileAbsolutePath : file_list) {
				String fileAbsPath = filePath + "/" + fileAbsolutePath;
				File file_abs = new File(fileAbsPath);
				fileInputStream = new FileInputStream(file_abs);
				byte[] byt = new byte[fileInputStream.available()];
				fileInputStream.read(byt);
				fileInputStream.close();
				String outPutDir = new File(
						new File(file_abs.getParent()).getParent()).getParent()
						+ "/result";
				FileOutputStream fileOutputStream2 = new FileOutputStream(
						new File(outPutDir + "/"
								+ new File(fileAbsPath).getName()));
				fileOutputStream2.write(byt);
				fileOutputStream2.close();
				file_abs.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return ActionSupport.SUCCESS;
	}
}
