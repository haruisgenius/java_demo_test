package com.example.java_demo_test;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.service.ifs.OrderService;
import com.example.java_demo_test.vo.OrderResponse;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class OrderTest {
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void addMenuTest() {
//		List<Menu> m1 = new Menu<>("beef", 120);         �X�ֶi�A��
		List<Menu> list = new ArrayList<>(Arrays.asList(new Menu("beef", 120),new Menu("fish", 100)));
		OrderResponse response = orderService.addMenus(list);
		List<Menu> responseList = response.getMenus();
		Assert.isTrue(responseList != null, "�s�W�\�I���~");
//		for(Menu menu : responseList){
//			System.out.println("�\�I�W��: "+ menu.getMenuName()+ "�\�I����: "+menu.getMenuPrice());
//		}
	}
	
}
