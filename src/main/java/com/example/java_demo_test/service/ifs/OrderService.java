package com.example.java_demo_test.service.ifs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderResponse;

public interface OrderService {
//	postman, controller要寫
	public GetMenuResponse getMenuByName(String name); //新增取出單一(屬性)menu及message, name=餐點名稱
	
	public GetMenuResponse getMenuByItem(String name);
	
	public OrderResponse orderHomework(Map<String, Integer> orderMap);
	
	public OrderResponse orderHomeworkByT(Map<String, Integer> orderMap);
	
	public OrderResponse getAllMenus();
	
	public OrderResponse addMenus(List<Menu> menus); //原本是void創完OrderResponse改屬性
	
	public OrderResponse order(Map<String, Integer> orderMap);
	
	public OrderResponse updateMenuPrice(List<Menu> menus);

	
}
