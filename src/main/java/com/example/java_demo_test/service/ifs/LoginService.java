package com.example.java_demo_test.service.ifs;

import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.vo.LoginRequest;
import com.example.java_demo_test.vo.LoginResponse;

public interface LoginService {
	
	public LoginResponse addAccount(LoginRequest request);
	
	public LoginResponse activeAccount(LoginRequest request);
	
	public LoginResponse loginAccount(LoginRequest request);
	
	public LoginResponse findByCityOrderByAgeAsc(LoginRequest request);

}
