package com.mycompany.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class FlumeSaveAction {
	public String execute() {
		

		try {
			ServletContext context = ServletActionContext.getServletContext();
			String filePath = context.getRealPath("") + "/result";
			File file = new File(filePath);
			String[] file_list = file.list();
			for (String fileAbsolutePath : file_list) {
				String fileAbsPath = filePath + "/" + fileAbsolutePath;
				File file_abs = new File(fileAbsPath);
				FileInputStream fileInputStream = new FileInputStream(file_abs);
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
		}

		return ActionSupport.SUCCESS;
	}

	public static void main(String[] args) {
		FlumeSaveAction filAction = new FlumeSaveAction();
		filAction.execute();
	}
}
