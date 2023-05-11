package com.example.java_demo_test.vo;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {

	@JsonProperty("menu_list") //πÔ¿≥postman¶W∫Ÿ
	private List<Menu> menuList;

	private String name;
	
	private Map<String, Integer> orderMap;
	
	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Integer> orderMap) {
		this.orderMap = orderMap;
	}

}
