package com.mycompany.vo;

public class ResponseVo {
	private String id;
	private String name;
	private String location;
	private String text;
	private String hashTag;
	private String retweetCount;
	private String createdAt;
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public String getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(String retweetCount) {
		this.retweetCount = retweetCount;
	}

}
