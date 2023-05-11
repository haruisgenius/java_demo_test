package com.example.java_demo_test.vo;

import com.example.java_demo_test.entity.Menu;

public class GetMenuResponse {

	private Menu menu;

	private String message;

	// ===============================

	public GetMenuResponse() {
		
	}

	public GetMenuResponse(Menu menu, String message) {
		this.menu = menu;
		this.message = message;
	}

	public GetMenuResponse(String message) {
		super();
		this.message = message;
	}

	// ===============================
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
