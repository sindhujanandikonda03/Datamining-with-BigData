package com.mycompany.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.struts2.ServletActionContext;

import com.mycompany.vo.FileListVo;
import com.opensymphony.xwork2.ActionSupport;

public class FileListAction {
	private List<FileListVo> fileList = new ArrayList<FileListVo>();
	
	public List<FileListVo> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileListVo> fileList) {
		this.fileList = fileList;
	}

	public String execute() {
		try {
			ServletContext context = ServletActionContext.getServletContext();
			String filePath = context.getRealPath("") + "/" + "config.properties";
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
			for (int i = 0; i < status.length; i++) {
				fs.delete(status[i].getPath(), true);
			}
			fileInputStream.close();
			fs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath("")+"/"+"result";
		File flumeList=new File(filePath);
		String[] list=flumeList.list();
		for(String path:list)
		{
		File file=new File(path);
		FileListVo fileListVo=new FileListVo();
		fileListVo.setFileName(file.getName());
		fileList.add(fileListVo);
		}
		return ActionSupport.SUCCESS;
	}
}
