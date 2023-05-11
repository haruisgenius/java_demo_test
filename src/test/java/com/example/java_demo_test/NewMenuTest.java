package com.example.java_demo_test;

import java.util.UUID;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.java_demo_test.entity.NewMenu;
import com.example.java_demo_test.respository.NewMenu2Dao;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class NewMenuTest {

	@Autowired
	private NewMenu2Dao newMenuDao;
	
	@Test
	private void addDataTest() {
		NewMenu nm1 = new NewMenu("fish", "·Î", 100, UUID.randomUUID());
//		NewMenu nm2 = new NewMenu("fish", "µN", 120);
//		newMenuDao.saveAll(Arrays.asList(nm1));
		
		
		
	}
	
}
