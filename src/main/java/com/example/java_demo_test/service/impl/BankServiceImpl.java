package com.example.java_demo_test.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.respository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

@Service
public class BankServiceImpl implements BankService {

	@Autowired // 用Autowired才能把interface叫出來
	private BankDao bankDao;

	@Override // 實作BankService介面, 裡面的addInfo方法()也要加Bank bank
	public void addInfo(Bank bank) {
		// 判斷帳號不得為null, 含特殊符號, 且長度為3~8
		checkAccount(bank);
		// 檢查密碼不得為null, 含空白, 長度為8~16, 包含特殊字元
		checkPwd(bank);
		// 檢查餘額不得為負數
		if (bank.getAmount() < 0) {
			System.out.println("資料錯誤");
			return;
		}
		// 新增資料到bank這張表
		bankDao.save(bank);

	}

	private void checkAccount(Bank bank) {
		if (bank == null) {
			System.out.println("Bank is null!");
			return; // void直接寫return就直接跳出此方法
		}
		String account = bank.getAccount();
		if (account == null) {
			System.out.println("Account is null!");
			return;
		}
		String pattern = "[\\w&&[^_]]{3,8}";
		if (!bank.getAccount().matches(pattern)) {
			System.out.println("資料錯誤");
			return;
		}
	}

	private void checkPwd(Bank bank) {
		String password = bank.getPwd();
		if (password == null) {
			System.out.println("Password is null!");
			return;
		}
		String patternPwd = "[\\S]{8,16}"; // \\S除空白定位Tab鍵換行煥頁以外其他字元
		if (!bank.getPwd().matches(patternPwd)) {
			System.out.println("資料錯誤");
			return;
		}
	}

	@Override
	public Bank getAmountById(String id) {
		if (!StringUtils.hasText(id)) { // = if(id == null || id.isBlank())
			return new Bank();
		}
		Optional<Bank> op = bankDao.findById(id); // Optional:值的容器
		if (!op.isPresent()) { // 判斷沒有值(包括null=沒值)
			return new Bank();
		}
		return op.get();
//71~74 =	return op.orElse(new Bank();

	}

	@Override
	public BankResponse deposit(Bank bank) { //存款
		//防呆
		if (bank == null 
			|| !StringUtils.hasText(bank.getAccount()) 
			|| !StringUtils.hasText(bank.getPwd()) 
			|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "帳號或密碼錯誤"); //找不到回傳null(有個東西可以回傳)
		}
		        //Bank → @Id
//		Optional<Bank> op = bankDao.findById(bank.getAccount());
//		if(!op.isPresent()) {
//			return new Bank();
//		}
//		Bank resBank = op.get();
//		if(!resBank.getPwd().equals(bank.getPwd())){
//		   return new Bank();
//		}
		
//		=88~96
		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if(resBank == null) {
			return new BankResponse(new Bank(), "資料不存在");
		}
		
		int oldAmount = resBank.getAmount();
		int depositAmount = bank.getAmount(); //bank.~~→欲存款金額
		int newAmount = oldAmount + depositAmount;
		resBank.setAmount(newAmount); //新的金額加進撈出來的resBank(設定存款後的金額)
		
		return new BankResponse(bankDao.save(resBank), "存款成功"); // =101~103
		
//		Bank newBank = bankDao.save(resBank); //set好的金額save進bankDao資料庫
		//回傳資料型態是Bank
//		return newBank;

	}

	@Override
	public BankResponse withdraw(Bank bank) { //提款
		//防呆
		if (bank == null 
			|| !StringUtils.hasText(bank.getAccount()) 
			|| !StringUtils.hasText(bank.getPwd()) 
			|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "帳號或密碼錯誤"); //找不到回傳null(有個東西可以回傳)
		}

		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if(resBank == null) { 
			return new BankResponse(new Bank(), "資料不存在");
		}
		
		int oldAmount = resBank.getAmount();
		int withdraw = bank.getAmount(); //bank.~~→欲存款金額
		
//                                    提領金額不得多於存款金額, 已用resBank接出資訊
		if(withdraw > oldAmount) { //= || bank.getAmount() > resBank.getAmount()
			return new BankResponse(new Bank(), "餘額不足");
		}
		
		int newAmount = oldAmount - withdraw;
		resBank.setAmount(newAmount); //新的金額加進撈出來的resBank(設定存款後的金額)
		
		return new BankResponse(bankDao.save(resBank), "提款成功"); 

	}
	
	
}
