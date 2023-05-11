package com.example.java_demo_test.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "register")
public class Register {
	
	@Id
	@Column(name = "account")
	private String account;
	
	@Column(name = "pwd")
	private String password;
	
	@Column(name = "reg_time")
	private LocalDateTime regTime = LocalDateTime.now();
	
	@Column(name = "active")
	private boolean active;

	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Register(String account, String password) {
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

	public LocalDateTime getRegTime() {
		return regTime;
	}

	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
