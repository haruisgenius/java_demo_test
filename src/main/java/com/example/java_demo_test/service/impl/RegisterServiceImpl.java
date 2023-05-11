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
			return new RegisterResponse("請輸入資料");
		}
		if(registerDao.existsById(account)) {
			return new RegisterResponse("此帳號已存在");
		}
		Register register = new Register(account, password);
//		register.setAccount(request.getAccount());
//		register.setPassword(request.getPassword());
//		register.setRegTime(LocalDateTime.now());
		registerDao.save(register);
		return new RegisterResponse("建立帳號成功");
	}

	@Override
	public RegisterResponse active(RegisterRequest request) {
		if(!StringUtils.hasText(request.getAccount()) || !StringUtils.hasText(request.getPassword())) {
			return new RegisterResponse("請輸入資料");
		}
		Register register = registerDao.findByAccountAndPassword(request.getAccount(), request.getPassword());
		if(register == null) {
			return new RegisterResponse("此帳號不存在");
		}
		if(register.isActive()) {
			return new RegisterResponse("此帳號已生效");
		}
		register.setActive(true);
		registerDao.save(register);
		return new RegisterResponse("生效帳號成功");
	}

	@Override
	public RegisterResponse login(RegisterRequest request) {
		if(!StringUtils.hasText(request.getAccount()) || !StringUtils.hasText(request.getPassword())) {
			return new RegisterResponse("請輸入資料");
		}
		Register register = registerDao.findByAccountAndPasswordAndActive(request.getAccount(), request.getPassword(), true);
		if(register == null) {
			return new RegisterResponse("此帳號不存在或尚未生效");
		}
		
		return new RegisterResponse("登入成功");
	}

	@Override
	public RegisterResponse getRegTime(String account, String password) {
		
		Register register = registerDao.findByAccountAndPassword(account, password);
		if(register == null) {
			return new RegisterResponse("此帳號不存在");
		}
		if(!register.isActive()) {
			return new RegisterResponse("此帳號尚未生效");
		}
//		Register register = registerDao.findByAccountAndPasswordAndActive(account, pwd, true);
//		if(register == null) {
//			return new RegisterResponse("此帳號不存在或尚未生效");
//		}
		return new RegisterResponse(register.getRegTime(), "取得建立帳號時間成功");
	}

	@Override
	public RegisterResponse getRegTime2(RegisterRequest request, String account, String password, Integer verifyCode) {
		if(!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
			return new RegisterResponse("請先登入");
		}
		//verifyCode值為 null 之狀況 > 沒有先登入(3個資訊皆在登入時存進session) / 時效過期
		if(verifyCode == null || verifyCode != request.getVerifyCode()) {
			return new RegisterResponse("驗證碼錯誤");
		}
		Register reg = registerDao.findByAccountAndPasswordAndActive(account, password, true);
		if(reg == null) {
			return new RegisterResponse("請先登入");
		}
		return new RegisterResponse(reg.getRegTime(), "取得建立帳號時間成功");
	}



}
