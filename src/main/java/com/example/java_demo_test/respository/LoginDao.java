package com.example.java_demo_test.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.Login;

@Repository
public interface LoginDao extends JpaRepository<Login, String>{
	
	public Login findByAccountAndPassword(String account, String password);
	
	public List<Login> findByCityOrderByAgeAsc(String city);
	
}
