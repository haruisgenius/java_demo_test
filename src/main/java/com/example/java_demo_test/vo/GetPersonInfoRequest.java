package com.example.java_demo_test.vo;

import java.util.List;

import com.example.java_demo_test.entity.PersonInfo;

public class GetPersonInfoRequest {
	
	private String id;
	
	private List<PersonInfo> personInfoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
