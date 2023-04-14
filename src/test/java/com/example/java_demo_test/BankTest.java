package com.example.java_demo_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.respository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

//Springboot������, �n��test�~�i�H�]springboot���\��
@SpringBootTest(classes = JavaDemoTestApplication.class)
public class BankTest {

	@Autowired //��autowired�~���interface�s�X��
	private BankDao bankDao;
	
	@Autowired //��autowired�~���interface�s�X��
	private BankService bankService;
	
	@Test        //���ժ���k
	public void addBankInfo() {
		Bank bank = new Bank("A01", "AA123", 1000);
		//�s�W��ƨ�bank�o�i��
		bankDao.save(bank);
	}
	
	@Test        //���ժ���k
	public void addInfoTest() {
		Bank bank = new Bank("AA999", "AA123456@", 2000);
		
		bankService.addInfo(bank);
	}
	
	@Test        //���ժ���k
	public void getAmountByIdTest() {
		Bank bank = bankService.getAmountById("A01");
		System.out.println("�b��"+bank.getAccount()+"�l�B"+bank.getAmount());
	}

	@Test        //���ժ���k
	public void depositTest() {
//		Bank bank = new Bank("AA999", "AA123456@", 2000);
//		Bank oldBank = bankDao.save(bank);
		//=44, 45 �Ыذ����
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 2000));
		//�s��
		Bank newBank = new Bank("AA999", "AA123456@", 3000);
		BankResponse response = bankService.deposit(newBank);
		//�T�{���B���s�i�h
		Bank resBank = response.getBank();
		Assert.isTrue(resBank.getAmount() == (oldBank.getAmount()+newBank.getAmount()), "�s�ڿ��~");
		//�R�����ո��
		bankDao.delete(resBank);
	}
	
	@Test        //���ժ���k
	public void withdrawTest() {
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 5000));
		//�s��
		Bank newBank = new Bank("AA999", "AA123456@", 3000);
		BankResponse response = bankService.withdraw(newBank);
		//�T�{������B
		Bank resBank = response.getBank();
		Assert.isTrue(resBank.getAmount() == (oldBank.getAmount() - newBank.getAmount()), "���ڿ��~");
		//�R�����ո��
		bankDao.delete(resBank);
	}
	
	
	
}
