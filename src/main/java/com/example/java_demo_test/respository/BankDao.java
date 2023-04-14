package com.example.java_demo_test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.Bank;

//�U�޸�Ʀs���w     ����Ʀs������object   //JpaRepository�i�H��application�̪�jpa����
@Repository
public interface BankDao extends JpaRepository<Bank, String>{ //T>type, class���W��, ID>@Id��ƫ��A
	
	public Bank findByAccountAndPwd(String account, String pwd);
}
