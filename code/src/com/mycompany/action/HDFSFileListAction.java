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
import com.opensymphony.xwork2.Action;

public class HDFSFileListAction {
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
			// fs.delete(pt, true);
			for (int i = 0; i < status.length; i++) {
				FileListVo fileListVo = new FileListVo();
				fileListVo.setFileName(status[i].getPath().toString());
				fileList.add(fileListVo);
			}
			fileInputStream.close();
			fs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
}
