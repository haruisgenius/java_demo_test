package com.example.java_demo_test.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Register;
import com.example.java_demo_test.respository.RegisterDao;
import com.example.java_demo_test.service.ifs.RegisterService;
import com.example.java_demo_test.vo.RegisterRequest;
import com.example.java_demo_test.vo.RegisterResponse;

@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Autowired
	private RegisterDao registerDao;
	
	@Override
	public RegisterResponse register(String account, String password) {
		if(!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
			return new RegisterResponse("�п�J���");
		}
		if(registerDao.existsById(account)) {
			return new RegisterResponse("���b���w�s�b");
		}
		Register register = new Register(account, password);
//		register.setAccount(request.getAccount());
//		register.setPassword(request.getPassword());
//		register.setRegTime(LocalDateTime.now());
		registerDao.save(register);
		return new RegisterResponse("�إ߱b�����\");
	}

	@Override
	public RegisterResponse active(RegisterRequest request) {
		if(!StringUtils.hasText(request.getAccount()) || !StringUtils.hasText(request.getPassword())) {
			return new RegisterResponse("�п�J���");
		}
		Register register = registerDao.findByAccountAndPassword(request.getAccount(), request.getPassword());
		if(register == null) {
			return new RegisterResponse("���b�����s�b");
		}
		if(register.isActive()) {
			return new RegisterResponse("���b���w�ͮ�");
		}
		register.setActive(true);
		registerDao.save(register);
		return new RegisterResponse("�ͮıb�����\");
	}

	@Override
	public RegisterResponse login(RegisterRequest request) {
		if(!StringUtils.hasText(request.getAccount()) || !StringUtils.hasText(request.getPassword())) {
			return new RegisterResponse("�п�J���");
		}
		Register register = registerDao.findByAccountAndPasswordAndActive(request.getAccount(), request.getPassword(), true);
		if(register == null) {
			return new RegisterResponse("���b�����s�b�Ω|���ͮ�");
		}
		
		return new RegisterResponse("�n�J���\");
	}

	@Override
	public RegisterResponse getRegTime(String account, String password) {
		
		Register register = registerDao.findByAccountAndPassword(account, password);
		if(register == null) {
			return new RegisterResponse("���b�����s�b");
		}
		if(!register.isActive()) {
			return new RegisterResponse("���b���|���ͮ�");
		}
//		Register register = registerDao.findByAccountAndPasswordAndActive(account, pwd, true);
//		if(register == null) {
//			return new RegisterResponse("���b�����s�b�Ω|���ͮ�");
//		}
		return new RegisterResponse(register.getRegTime(), "���o�إ߱b���ɶ����\");
	}

	@Override
	public RegisterResponse getRegTime2(RegisterRequest request, String account, String password, Integer verifyCode) {
		if(!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
			return new RegisterResponse("�Х��n�J");
		}
		//verifyCode�Ȭ� null �����p > �S�����n�J(3�Ӹ�T�Ҧb�n�J�ɦs�isession) / �ɮĹL��
		if(verifyCode == null || verifyCode != request.getVerifyCode()) {
			return new RegisterResponse("���ҽX���~");
		}
		Register reg = registerDao.findByAccountAndPasswordAndActive(account, password, true);
		if(reg == null) {
			return new RegisterResponse("�Х��n�J");
		}
		return new RegisterResponse(reg.getRegTime(), "���o�إ߱b���ɶ����\");
	}



}
