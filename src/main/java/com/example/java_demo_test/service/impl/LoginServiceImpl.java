package com.example.java_demo_test.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.contants.RtnCode;
import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.respository.LoginDao;
import com.example.java_demo_test.service.ifs.LoginService;
import com.example.java_demo_test.vo.LoginRequest;
import com.example.java_demo_test.vo.LoginResponse;
import com.example.java_demo_test.vo.LoginWithoutPwdResponse;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginDao loginDao;

	@Override
	public LoginResponse addAccount(LoginRequest request) {
		LoginResponse logResAct = checkAccount(request.getAccount());
		if(logResAct != null) { //防呆都通過回傳null
			return logResAct; //結果(包括印的東西)被logResAct接出來
		}
		LoginResponse logResPwd = checkPwd(request.getPassword());
		if(logResPwd != null) {
			return logResPwd;
		}
		request.setRegTime(LocalDateTime.now());
		Login response = new Login(request.getAccount(), request.getPassword(), 
				request.getName(), request.getAge(), request.getCity(), request.getRegTime());
		loginDao.save(response);
		return new LoginResponse(response, RtnCode.SUCCESSFUL.getMessage());
	}
	
	private LoginResponse checkAccount(String request) {
		if(request == null) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		String pattern = "\\w{3,8}";
		if(!request.matches(pattern)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		if(loginDao.existsById(request)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		return null;
	}
	
	private LoginResponse checkPwd(String request) {
		if(request == null) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		String pattern = "\\w{8,12}";
		// \p表比對字元具備某特性 Punct表標點符號 !"#$%&’()*+,-./:;<=>?@[]^_`{|}~
		String pattern2 = "^(?=.*[\\p{Punct}])[\\S]{8,12}$"; //.*或.+拿掉等於[]裡的全都要
		//=^(?=.*[\\S^\\w])[\\S][8,12}$
		if(!request.matches(pattern)&&!request.matches(pattern2)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		return null;
	}

	
	@Override
	public LoginResponse activeAccount(LoginRequest request) {
		if(request == null || !StringUtils.hasText(request.getAccount()) || 
				!StringUtils.hasText(request.getPassword())) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		Login resLogin = loginDao.findByAccountAndPassword(request.getAccount(), request.getPassword());
		if(resLogin == null) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		if(resLogin.isActive() == true) {
			return new LoginResponse(RtnCode.SUCCESSFUL.getMessage());
		}
		
		resLogin.setActive(true);
		return new LoginResponse(loginDao.save(resLogin), RtnCode.SUCCESSFUL.getMessage());
	}


	@Override
	public LoginResponse loginAccount(LoginRequest request) {
		if(!StringUtils.hasText(request.getAccount()) || 
				!StringUtils.hasText(request.getPassword())) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		Login resLogin = loginDao.findByAccountAndPassword(request.getAccount(), request.getPassword());
		if(resLogin == null) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		if(resLogin.isActive() == false) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		return new LoginResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public LoginResponse findByCityOrderByAgeAsc(LoginRequest request) {
		List<Login> result = loginDao.findByCityOrderByAgeAsc(request.getCity());
		if(result.size() == 0) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		for(Login item : result) {
			request.setPassword(null);
		}
		return new LoginResponse(result, RtnCode.SUCCESSFUL.getMessage());
	}
	
}
