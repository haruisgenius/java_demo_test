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

	@Autowired // ��Autowired�~���interface�s�X��
	private BankDao bankDao;

	@Override // ��@BankService����, �̭���addInfo��k()�]�n�[Bank bank
	public void addInfo(Bank bank) {
		// �P�_�b�����o��null, �t�S��Ÿ�, �B���׬�3~8
		checkAccount(bank);
		// �ˬd�K�X���o��null, �t�ť�, ���׬�8~16, �]�t�S��r��
		checkPwd(bank);
		// �ˬd�l�B���o���t��
		if (bank.getAmount() < 0) {
			System.out.println("��ƿ��~");
			return;
		}
		// �s�W��ƨ�bank�o�i��
		bankDao.save(bank);

	}

	private void checkAccount(Bank bank) {
		if (bank == null) {
			System.out.println("Bank is null!");
			return; // void�����greturn�N�������X����k
		}
		String account = bank.getAccount();
		if (account == null) {
			System.out.println("Account is null!");
			return;
		}
		String pattern = "[\\w&&[^_]]{3,8}";
		if (!bank.getAccount().matches(pattern)) {
			System.out.println("��ƿ��~");
			return;
		}
	}

	private void checkPwd(Bank bank) {
		String password = bank.getPwd();
		if (password == null) {
			System.out.println("Password is null!");
			return;
		}
		String patternPwd = "[\\S]{8,16}"; // \\S���ťթw��Tab�䴫��ح��H�~��L�r��
		if (!bank.getPwd().matches(patternPwd)) {
			System.out.println("��ƿ��~");
			return;
		}
	}

	@Override
	public Bank getAmountById(String id) {
		if (!StringUtils.hasText(id)) { // = if(id == null || id.isBlank())
			return new Bank();
		}
		Optional<Bank> op = bankDao.findById(id); // Optional:�Ȫ��e��
		if (!op.isPresent()) { // �P�_�S����(�]�Anull=�S��)
			return new Bank();
		}
		return op.get();
//71~74 =	return op.orElse(new Bank();

	}

	@Override
	public BankResponse deposit(Bank bank) { //�s��
		//���b
		if (bank == null 
			|| !StringUtils.hasText(bank.getAccount()) 
			|| !StringUtils.hasText(bank.getPwd()) 
			|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "�b���αK�X���~"); //�䤣��^��null(���ӪF��i�H�^��)
		}
		        //Bank �� @Id
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
			return new BankResponse(new Bank(), "��Ƥ��s�b");
		}
		
		int oldAmount = resBank.getAmount();
		int depositAmount = bank.getAmount(); //bank.~~�����s�ڪ��B
		int newAmount = oldAmount + depositAmount;
		resBank.setAmount(newAmount); //�s�����B�[�i���X�Ӫ�resBank(�]�w�s�ګ᪺���B)
		
		return new BankResponse(bankDao.save(resBank), "�s�ڦ��\"); // =101~103
		
//		Bank newBank = bankDao.save(resBank); //set�n�����Bsave�ibankDao��Ʈw
		//�^�Ǹ�ƫ��A�OBank
//		return newBank;

	}

	@Override
	public BankResponse withdraw(Bank bank) { //����
		//���b
		if (bank == null 
			|| !StringUtils.hasText(bank.getAccount()) 
			|| !StringUtils.hasText(bank.getPwd()) 
			|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "�b���αK�X���~"); //�䤣��^��null(���ӪF��i�H�^��)
		}

		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if(resBank == null) { 
			return new BankResponse(new Bank(), "��Ƥ��s�b");
		}
		
		int oldAmount = resBank.getAmount();
		int withdraw = bank.getAmount(); //bank.~~�����s�ڪ��B
		
//                                    ������B���o�h��s�ڪ��B, �w��resBank���X��T
		if(withdraw > oldAmount) { //= || bank.getAmount() > resBank.getAmount()
			return new BankResponse(new Bank(), "�l�B����");
		}
		
		int newAmount = oldAmount - withdraw;
		resBank.setAmount(newAmount); //�s�����B�[�i���X�Ӫ�resBank(�]�w�s�ګ᪺���B)
		
		return new BankResponse(bankDao.save(resBank), "���ڦ��\"); 

	}
	
	
}
