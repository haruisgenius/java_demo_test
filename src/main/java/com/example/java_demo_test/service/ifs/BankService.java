package com.example.java_demo_test.service.ifs;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.vo.BankResponse;

public interface BankService {
	                   //Bank�̤w�g���]�t�T�ӰѼ�
	public void addInfo(Bank bank); //�w�q��k����public
	
	public Bank getAmountById(String id);
	
	public BankResponse deposit(Bank bank);
	
	public BankResponse withdraw(Bank bank);
	
	
}
