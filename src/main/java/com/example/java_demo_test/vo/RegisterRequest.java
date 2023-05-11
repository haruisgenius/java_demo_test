package com.example.java_demo_test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {

	private String account;
	
	private String password;
	
	@JsonProperty("verify_code")
	private int verifyCode;

	

	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterRequest(String account, String password) {
		super();
		this.account = account;
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(int verifyCode) {
		this.verifyCode = verifyCode;
	}
	
}
