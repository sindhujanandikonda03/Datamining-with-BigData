package com.mycompany.action;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.mycompany.logic.ClusteringLogic;
import com.mycompany.logic.HiveClientCall;
import com.mycompany.vo.HashTagVo;
import com.mycompany.vo.LocationVo;
import com.mycompany.vo.RetweetCountVo;
import com.opensymphony.xwork2.ActionSupport;

public class ClusteringAction {
	private List<RetweetCountVo> retweetList;
	private String mode;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<RetweetCountVo> getRetweetList() {
		return retweetList;
	}

	public void setRetweetList(List<RetweetCountVo> retweetList) {
		this.retweetList = retweetList;
	}

	ClusteringLogic clusteringLogic = new ClusteringLogic();
	private List<LocationVo> locationList;

	private List<HashTagVo> hashTagList;
	
	
	

	public List<HashTagVo> getHashTagList() {
		return hashTagList;
	}

	public void setHashTagList(List<HashTagVo> hashTagList) {
		this.hashTagList = hashTagList;
	}

	public List<LocationVo> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationVo> locationList) {
		this.locationList = locationList;
	}

	public String execute() {
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getRealPath("")+"/result";
		if (mode.equals("Location")) {
			locationList = clusteringLogic.LocationServices(filePath);
		}
		else if(mode.equals("HashTags")) {
			hashTagList = clusteringLogic.HashTagServices(filePath);
		}
		else if(mode.equals("retweet")) {
			retweetList=clusteringLogic.retweetCountServices(filePath);
		}
		return ActionSupport.SUCCESS;
	}
}
