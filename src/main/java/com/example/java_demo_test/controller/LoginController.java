package com.example.java_demo_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.service.ifs.LoginService;
import com.example.java_demo_test.vo.LoginRequest;
import com.example.java_demo_test.vo.LoginResponse;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping(value = "add_account")
	public LoginResponse addAccount(@RequestBody LoginRequest request) {
		return loginService.addAccount(request);
	}
	
	@PostMapping(value = "active_account")
	public LoginResponse activeAccount(@RequestBody LoginRequest request) {
		return loginService.activeAccount(request);
	}
	
	@PostMapping(value = "login_account")
	public LoginResponse loginAccount(@RequestBody LoginRequest request) {
		return loginService.loginAccount(request);
	}
	
	@PostMapping(value = "find_account_by_city_orderby_age_asc")
	public LoginResponse findByCityOrderByAgeAsc(@RequestBody LoginRequest request) {
		return loginService.findByCityOrderByAgeAsc(request);
	}

}
