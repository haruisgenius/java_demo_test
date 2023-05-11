package com.example.java_demo_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.respository.OrderDao;
import com.example.java_demo_test.service.ifs.OrderService;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderRequest;
import com.example.java_demo_test.vo.OrderResponse;

import io.swagger.v3.oas.annotations.Hidden;

//import springfox.documentation.annotations.ApiIgnore;

@RestController //已包含@ResponseBody
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
//	@ApiIgnore //Swagger 隱藏
	@Hidden //open api隱藏
	@GetMapping(value = "get_all_menus")
	public OrderResponse getAllMenus(){
		return orderService.getAllMenus();
	}
	
	@PostMapping(value = "add_menus")
	public OrderResponse addMenus(@RequestBody OrderRequest request){
		return orderService.addMenus(request.getMenuList());
	}
	
	@PostMapping(value = "get_menus_by_name")
	//     (回傳)要找的東西OO        方法名稱  ( @才能做key值的對應   透過OO方法找到OO      ) 若找到則塞進OrderRequest內的list
	public GetMenuResponse getMenuByName(@RequestBody OrderRequest request){
//		                     (結果)Response        <>  (請求)OrderRequest名稱無實質意義, 辨識用
		return orderService.getMenuByName(request.getName());
	}
	
	@PostMapping(value = "order")
	public OrderResponse orderHomework(@RequestBody OrderRequest request){
		return orderService.orderHomework(request.getOrderMap());
	}
	
	public GetMenuResponse getMenuByItem(@RequestBody OrderRequest request) {
		return orderService.getMenuByItem(request.getName());
	}
	
	
}
