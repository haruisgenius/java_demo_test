package com.example.java_demo_test.respository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.PersonInfo;

public class PersonInfoDaoImpl extends BaseDao{
	
	public List<PersonInfo> doQueryByAge(int age){
		StringBuffer sb = new StringBuffer(); //SQL粂猭琌﹃
//		                      EntityName        entityず把计
		sb.append("select P from PersonInfo P where P.age >= :age"); //where兵ンin絛瞅
		Map<String, Object> params = new HashMap<>();
//		"age" = 14︽:, age = 11︽よ猭把计age
		params.put("age", age);
		return doQuery(sb.toString(), params, PersonInfo.class);
	}
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize){
		StringBuffer sb = new StringBuffer(); //SQL粂猭琌﹃
//		                      EntityName        entityず把计
		sb.append("select P from PersonInfo P where P.age >= :age"); //where兵ンin絛瞅
		Map<String, Object> params = new HashMap<>();
//		"age" = 14︽:, age = 11︽よ猭把计age
		params.put("age", age);
		return doQuery(sb.toString(), params, PersonInfo.class, limitSize);
	}
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize, int startPosition){
		StringBuffer sb = new StringBuffer(); //SQL粂猭琌﹃
//		                      EntityName        entityず把计
		sb.append("select P from PersonInfo P where P.age >= :age"); //where兵ンin絛瞅
		Map<String, Object> params = new HashMap<>();
//		"age" = 14︽:, age = 11︽よ猭把计age
		params.put("age", age);
		return doQuery(sb.toString(), params, PersonInfo.class, limitSize, startPosition);
	}
	
	public int UpdateAgeByName(int age, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("update PersonInfo set age = :age where name = :name");
		Map<String, Object> params = new HashMap<>();
		params.put("age", age);
		params.put("name", name);
		//    BaseDaoよ猭
		return doUpdate(sb.toString(), params);
	}
	
	public List<PersonInfo> findByNameOrCity(String name, String city){ //JPA
		name = !StringUtils.hasText(name) ? name : null;
		city = !StringUtils.hasText(city) ? city : null;
		if(name == null && city == null) {
//			doQuery(city, name, null);
		}
		return null;
	}
	
	
}
