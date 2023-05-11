package com.example.java_demo_test.vo;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;

public class OrderResponse {

	private List<Menu> menus;

	private Map<String, Integer> orderMap;

	private int totalPrice;

	private String message;

	public OrderResponse() {
		super();
	}

	public OrderResponse(List<Menu> menus, Map<String, Integer> orderMap, int totalPrice, String message) {
		this.menus = menus;
		this.orderMap = orderMap;
		this.totalPrice = totalPrice;
		this.message = message;
	}

	public OrderResponse(String message) {
		super();
		this.message = message;
	}

	public OrderResponse(List<Menu> menus) {
		super();
		this.menus = menus;
	}

	public OrderResponse(List<Menu> menus, String message) {
		super();
		this.menus = menus;
		this.message = message;
	}

	public OrderResponse(Map<String, Integer> orderMap, int totalPrice, String message) {
		super();
		this.orderMap = orderMap;
		this.totalPrice = totalPrice;
		this.message = message;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Map<String, Integer> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Integer> orderMap) {
		this.orderMap = orderMap;
	}

}
