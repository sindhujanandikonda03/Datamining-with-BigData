package com.mycompany.action;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.mycompany.logic.JsonConverter;
import com.mycompany.vo.ResponseVo;
import com.opensymphony.xwork2.ActionSupport;

public class ResponseAction {
	private JsonConverter jsonConverter;
	private String ids;
	private List<ResponseVo> responseList;
	
	public List<ResponseVo> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<ResponseVo> responseList) {
		this.responseList = responseList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String execute() {
		jsonConverter=new JsonConverter();
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath("")+"/result/result.json";
		System.out.println("ids ::" + ids);
		responseList=jsonConverter.getResponseVos(filePath, ids);
		return ActionSupport.SUCCESS;
	}
}
