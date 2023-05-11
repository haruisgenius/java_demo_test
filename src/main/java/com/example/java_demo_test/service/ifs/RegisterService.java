package com.example.java_demo_test.service.ifs;

import com.example.java_demo_test.entity.Register;
import com.example.java_demo_test.vo.RegisterRequest;
import com.example.java_demo_test.vo.RegisterResponse;

public interface RegisterService {

	public RegisterResponse register(String account, String password);
	
	public RegisterResponse active(RegisterRequest request);
	
	public RegisterResponse login(RegisterRequest request);
	
	public RegisterResponse getRegTime(String account, String password);
	
	public RegisterResponse getRegTime2(RegisterRequest request, String account, String password, Integer verifyCode);
}
