package com.example.java_demo_test.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.Bank;

//託管資料存取庫     ↓資料存取物件object   //JpaRepository可以用application裡的jpa那串
@Repository
public interface BankDao extends JpaRepository<Bank, String>{ //T>type, class的名稱, ID>@Id資料型態
	
	public Bank findByAccountAndPwd(String account, String pwd);
}
