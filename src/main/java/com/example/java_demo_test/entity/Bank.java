package com.example.java_demo_test.entity;

import javax.persistence.Column;
import javax.persistence.Entity; //資料持久化
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank") // 連結資料庫workbench的哪一個表db(名字)
public class Bank {

	@Id
	@Column(name = "account") // column資料庫account欄位名稱做對應
	private String account;

	@Column(name = "password")
	private String pwd;

	@Column(name = "amount")
	private int amount;
	
	private String message;

	public Bank() {
		
	}

	public Bank(String account, String pwd, int amount) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
