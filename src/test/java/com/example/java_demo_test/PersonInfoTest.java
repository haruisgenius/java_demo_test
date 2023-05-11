package com.example.java_demo_test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.respository.PersonInfoDao;
import com.example.java_demo_test.service.ifs.PersonInfoService;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class PersonInfoTest {

	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Autowired
	private PersonInfoService personInfoservice;
	
	@Test
	public void updateNameByIdTest() {
		int result = personInfoDao.updateNameById("0001", "Haruka");
		System.out.println(result);
	}
	
	@Test
	public void doQueryByAgeTest() {
		List<PersonInfo> res = personInfoDao.doQueryByAge(30);
		System.out.println(res.size());
	}
	
	@Test
	public void UpdateAgeByNameTest() {
		int res = personInfoDao.UpdateAgeByName(38, "Haruka");
		System.out.println(res);
	}
	
	@Test
	public void findByNameOrCityTest() {
		String name = "Ha";
		String city = "";
		name = !StringUtils.hasText(name) ? name : "null";
		city = !StringUtils.hasText(city) ? city : "null";
		List<PersonInfo> res = personInfoDao.QueryByNameOrCity(name, city);
		for(PersonInfo item : res) {
			System.out.println(item.getName());
		}
	}
}
