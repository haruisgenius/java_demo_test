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

@RestController //�w�]�t@ResponseBody
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
//	@ApiIgnore //Swagger ����
	@Hidden //open api����
	@GetMapping(value = "get_all_menus")
	public OrderResponse getAllMenus(){
		return orderService.getAllMenus();
	}
	
	@PostMapping(value = "add_menus")
	public OrderResponse addMenus(@RequestBody OrderRequest request){
		return orderService.addMenus(request.getMenuList());
	}
	
	@PostMapping(value = "get_menus_by_name")
	//     (�^��)�n�䪺�F��OO        ��k�W��  ( @�~�వkey�Ȫ�����   �z�LOO��k���OO      ) �Y���h��iOrderRequest����list
	public GetMenuResponse getMenuByName(@RequestBody OrderRequest request){
//		                     (���G)Response        <>  (�ШD)OrderRequest�W�ٵL���N�q, ���ѥ�
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
