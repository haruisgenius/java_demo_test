package com.example.java_demo_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.respository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

//Springboot的測試, 要有test才可以跑springboot的功能
@SpringBootTest(classes = JavaDemoTestApplication.class)
public class BankTest {

	@Autowired //用autowired才能把interface叫出來
	private BankDao bankDao;
	
	@Autowired //用autowired才能把interface叫出來
	private BankService bankService;
	
	@Test        //測試的方法
	public void addBankInfo() {
		Bank bank = new Bank("A01", "AA123", 1000);
		//新增資料到bank這張表
		bankDao.save(bank);
	}
	
	@Test        //測試的方法
	public void addInfoTest() {
		Bank bank = new Bank("AA999", "AA123456@", 2000);
		
		bankService.addInfo(bank);
	}
	
	@Test        //測試的方法
	public void getAmountByIdTest() {
		Bank bank = bankService.getAmountById("A01");
		System.out.println("帳號"+bank.getAccount()+"餘額"+bank.getAmount());
	}

	@Test        //測試的方法
	public void depositTest() {
//		Bank bank = new Bank("AA999", "AA123456@", 2000);
//		Bank oldBank = bankDao.save(bank);
		//=44, 45 創建假資料
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 2000));
		//存款
		Bank newBank = new Bank("AA999", "AA123456@", 3000);
		BankResponse response = bankService.deposit(newBank);
		//確認金額有存進去
		Bank resBank = response.getBank();
		Assert.isTrue(resBank.getAmount() == (oldBank.getAmount()+newBank.getAmount()), "存款錯誤");
		//刪除測試資料
		bankDao.delete(resBank);
	}
	
	@Test        //測試的方法
	public void withdrawTest() {
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 5000));
		//存款
		Bank newBank = new Bank("AA999", "AA123456@", 3000);
		BankResponse response = bankService.withdraw(newBank);
		//確認提領金額
		Bank resBank = response.getBank();
		Assert.isTrue(resBank.getAmount() == (oldBank.getAmount() - newBank.getAmount()), "提款錯誤");
		//刪除測試資料
		bankDao.delete(resBank);
	}
	
	
	
}
