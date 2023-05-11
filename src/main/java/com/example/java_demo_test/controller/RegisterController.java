package com.example.java_demo_test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.service.ifs.RegisterService;
import com.example.java_demo_test.vo.RegisterRequest;
import com.example.java_demo_test.vo.RegisterResponse;
import com.mysql.cj.Session;

@RestController
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	
	@PostMapping(value = "register")
	private RegisterResponse register(@RequestBody RegisterRequest request) {
		return registerService.register(request.getAccount(), request.getPassword());
	}
	
	@PostMapping(value = "active")
	private RegisterResponse active(@RequestBody RegisterRequest request) {
		return registerService.active(request);
	}
	
	@PostMapping(value = "login") //                                     �Ȧs�O����Ŷ�
	private RegisterResponse login(@RequestBody RegisterRequest request, HttpSession session) {
		RegisterResponse res = registerService.login(request);
		if(res.getMessage().equalsIgnoreCase("�n�J���\")) {
			double random = Math.random()*10000; //�H���ͦ�4�ӼƦr
			int verifyCode = (int) Math.round(random); //�|�ˤ��J
			session.setAttribute("verifyCode", verifyCode);
			session.setAttribute("account", request.getAccount());
			session.setAttribute("password", request.getPassword());
			session.setMaxInactiveInterval(60); //�]�w�ɶ� ��쬰��
			res.setSessionId(session.getId()); //
			res.setVerifyCode(verifyCode);
		}
		return res;
	}
	
	@PostMapping(value = "getRegTime")
	private RegisterResponse getRegTime(@RequestBody RegisterRequest request) {
		return registerService.getRegTime(request.getAccount(), request.getPassword());
	}
	
	@PostMapping(value = "getRegTime1")
	private RegisterResponse getRegTime1(@RequestBody RegisterRequest request, HttpSession session) {
		String account = (String) session.getAttribute("account");
		String password = (String) session.getAttribute("password");
		if(!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
			return new RegisterResponse("�Х��n�J");
		}
		Integer verifyCode = (Integer) session.getAttribute("verifyCode");
		//�Ȭ� null �����p > �S�����n�J(3�Ӹ�T�Ҧb�n�J�ɦs�isession) / �ɮĹL��
		if(verifyCode == null || verifyCode != request.getVerifyCode()) {
			return new RegisterResponse("���ҽX���~");
		}
		return registerService.getRegTime(account, password);
	}
	
	@PostMapping(value = "getRegTime2")
	private RegisterResponse getRegTime2(@RequestBody RegisterRequest request, HttpSession session) {
		String account = (String) session.getAttribute("account");
		String password = (String) session.getAttribute("password");
		Integer verifyCode = (Integer) session.getAttribute("verifyCode");
		return registerService.getRegTime2(request, account, password, verifyCode);
	}
}
