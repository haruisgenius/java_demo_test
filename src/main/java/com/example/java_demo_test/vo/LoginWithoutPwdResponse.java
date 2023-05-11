package com.example.java_demo_test.vo;

import java.time.LocalDateTime;

public class LoginWithoutPwdResponse {
	
	private String account;
	
	private String name;
	
	private int age;
	
	private String city;
	
	private LocalDateTime regTime;
	
	private boolean isActive;

	public LoginWithoutPwdResponse() {
		super();
	}

	public LoginWithoutPwdResponse(String account, String name, int age, String city, LocalDateTime regTime, boolean isActive) {
		super();
		this.account = account;
		this.name = name;
		this.age = age;
		this.city = city;
		this.regTime = regTime;
		this.isActive = isActive;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getRegTime() {
		return regTime;
	}

	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
