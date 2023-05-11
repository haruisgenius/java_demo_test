package com.example.java_demo_test.service.ifs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderResponse;

public interface OrderService {
//	postman, controller�n�g
	public GetMenuResponse getMenuByName(String name); //�s�W���X��@(�ݩ�)menu��message, name=�\�I�W��
	
	public GetMenuResponse getMenuByItem(String name);
	
	public OrderResponse orderHomework(Map<String, Integer> orderMap);
	
	public OrderResponse orderHomeworkByT(Map<String, Integer> orderMap);
	
	public OrderResponse getAllMenus();
	
	public OrderResponse addMenus(List<Menu> menus); //�쥻�Ovoid�Ч�OrderResponse���ݩ�
	
	public OrderResponse order(Map<String, Integer> orderMap);
	
	public OrderResponse updateMenuPrice(List<Menu> menus);

	
}
