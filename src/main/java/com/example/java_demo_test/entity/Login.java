package com.example.java_demo_test.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "login")
@JsonInclude(JsonInclude.Include.NON_NULL) //如果欄位為null整行隱藏
public class Login {

	@Id
	@Column(name = "account")
	private String account;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@Column(name = "city")
	private String city;

	@Column(name = "register_time")
	private LocalDateTime regTime;
	
	/*
	 * private Date regTime;
	 * 產生Date => new Date();
	 * 
	 * private LocalDateTime regTime;
	 * 產生LocalDateTime => LocalDateTime.now();
	 */

	@Column(name = "active")
	private boolean isActive;

	public Login() {

	}

	public Login(String account, String password, String name, int age, String city, LocalDateTime regTime,
			Boolean active) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.age = age;
		this.city = city;
		this.regTime = regTime;

	}

	public Login(String account, String password, String name, int age, String city, LocalDateTime regTime) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.age = age;
		this.city = city;
		this.regTime = regTime;
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

	public boolean isActive() { //is 問True或False
		return isActive;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	
}
