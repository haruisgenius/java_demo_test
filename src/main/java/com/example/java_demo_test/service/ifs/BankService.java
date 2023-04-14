package com.example.java_demo_test.service.ifs;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.vo.BankResponse;

public interface BankService {
	                   //Bank裡已經有包含三個參數
	public void addInfo(Bank bank); //定義方法都用public
	
	public Bank getAmountById(String id);
	
	public BankResponse deposit(Bank bank);
	
	public BankResponse withdraw(Bank bank);
	
	
}
