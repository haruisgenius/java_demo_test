package com.example.java_demo_test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.swing.MenuSelectionManager;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.respository.OrderDao;
import com.example.java_demo_test.service.ifs.OrderService;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	public void orderMenus(List<Menu> menus) {
		if (menus == null) {

		}
		checkName(menus);
	}

	private void checkName(List<Menu> menus) {
		if (menus == null) {
			System.out.println("Menu is null");
			return;
		}
		// 輸入的是不是空
		for (Menu menusName : menus) {
			if (menusName.getItem() == null) {
				System.out.println("餐點名稱錯誤");
				return;
			}
		}
		// 餐點名稱重複
		for (int i = 0; i < menus.size() - 1; i++) {
			for (int j = i + 1; j < menus.size(); j++) {
				// 從list拿出來 拿出來的東西找餐點名字 比較內容
				if (menus.get(i).getItem().equals(menus.get(j).getItem())) {
					System.out.println("!餐點名稱重複!");
					return;
				}
			}
		}
	}

	private void checkPrice(List<Menu> menus) {
		if (menus == null) {
			System.out.println("Price is null");
			return;
		}
		for (int i = 0; i < menus.size() - 1; i++) {
			// 餐點價格不得為零或為負
			if (menus.get(i).getPrice() <= 0) {
				System.out.println("!餐點價格錯誤!");
				return;
			}
		}
	}

	@Override
	public OrderResponse addMenus(List<Menu> menus) {
		// 若陣列為空 則不會進for直接執行97儲存並印新增成功
		if (CollectionUtils.isEmpty(menus)) { // =menus == null || menus.isEmpty()
			return new OrderResponse("新增餐點錯誤");
		}
		for (Menu item : menus) {
			// 餐點名稱不是空白or空字串 hasText(表有東西)>用反向(!)
			if (!StringUtils.hasText(item.getItem())) {
				return new OrderResponse("餐點名稱錯誤");
			}
			if (item.getPrice() <= 0) {
				return new OrderResponse("餐點價格錯誤");
			}
		}
		// list, set多筆>saveAll/ map單筆
		List<Menu> response = orderDao.saveAll(menus);
		return new OrderResponse(response, "新增餐點成功");
//	=	OrderResponse res = new OrderResponse();
//		res.setMenus(response);
//		res.setMessage("新增餐點成功");
//		return res;
	}

	@Override
	public OrderResponse order(Map<String, Integer> orderMap) {
		List<String> itemList = new ArrayList<>();
		for (Entry<String, Integer> item : orderMap.entrySet()) { // map的foreach(餐點名稱, 數量)
			if (item.getValue() < 0) {
				return new OrderResponse("餐點數量錯誤");
			}
			itemList.add(item.getKey());
		}
		List<Menu> result = orderDao.findAllById(itemList); // 找點餐的所有餐點
		int totalPrice = 0;
		Map<String, Integer> finalOrderMap = new HashMap<>();
		for (Menu menu : result) {
			String item = menu.getItem();
			int price = menu.getPrice();
			for (Entry<String, Integer> map : orderMap.entrySet()) {
				String key = map.getKey(); // orderMap裡的餐點名稱
				int value = map.getValue(); // orderMap裡的餐點數量
				if (item.equals(key)) {
					int singleTotalPrice = price * value; // 價格*數量
					totalPrice += singleTotalPrice;
					finalOrderMap.put(key, value); // 放實際上有存在的餐點
				}
			}
		}
		totalPrice = totalPrice > 500 ? (int) (totalPrice * 0.9) : totalPrice;
		return new OrderResponse(finalOrderMap, totalPrice, "點餐成功");
	}

	@Override
	public OrderResponse getAllMenus() {
		return new OrderResponse(orderDao.findAll(), "取得餐點成功");
	}

	@Override
	public GetMenuResponse getMenuByName(String name) {
		if (!StringUtils.hasText(name)) {
			return new GetMenuResponse("請輸入有效餐點名稱");
		}
		Optional<Menu> op = orderDao.findById(name); // Optional唯一物件
		if (op.isEmpty()) {
			return new GetMenuResponse("取得餐點失敗");
		}
		return new GetMenuResponse(op.get(), "取得菜單成功");
	}

//	=getMenuByName
	public GetMenuResponse getMenuByItem(String name) {
		if (!StringUtils.hasText(name)) {
			return new GetMenuResponse("請輸入有效餐點名稱");
		}
		Menu menu = orderDao.findByItem(name);
		if (menu == null) {
			return new GetMenuResponse("取得餐點失敗");
		}
		return new GetMenuResponse(menu, "成功");
	}

	@Override
	public OrderResponse updateMenuPrice(List<Menu> menus) { // beef:150; AAA:50; tea:60
		if (CollectionUtils.isEmpty(menus)) {
			return new OrderResponse("菜單錯誤");
		}
		// 將輸入菜單加入list
		List<String> ids = new ArrayList<>();
		for (Menu menu : menus) {

			if (menu.getPrice() < 0) {
				return new OrderResponse("菜單價格錯誤");
			}
			ids.add(menu.getItem()); // 只存名稱ids=["beef", "AAA", "tea"]
		}
		List<Menu> res = orderDao.findAllById(ids); // res(in DB) = beef:120; tea:30
		if (ids.size() == 0) {
			return new OrderResponse("查無菜單");
		}
		List<Menu> newMenuList = new ArrayList<>();
		for (Menu resMenu : res) {
			String itemInDB = resMenu.getItem();
			for (Menu menu : menus) { // 原本的菜單
				String updateItem = menu.getItem();
				if (itemInDB.equals(updateItem)) {
					newMenuList.add(menu);
				}
			}
		}
		return new OrderResponse(orderDao.saveAll(newMenuList), "更新成功");
	}

	@Override
	public OrderResponse orderHomework(Map<String, Integer> orderMap) {
		// 點的餐點(只有key > 型態為String) > 要去找價格
		List<String> orderMenuFindPrice = new ArrayList<>();
		//int>單品總價
		Map<String, Integer> orderMapInMenu= new HashMap<>(); 
		int totalPrice = 0;
		//點餐之orderMap的東西拿出來加進menu list
		for (Entry<String, Integer> menu : orderMap.entrySet()) {
			//        判斷資料庫有相同菜單名(只有key)>加進list
			if (orderDao.existsById(menu.getKey())) {
				orderMenuFindPrice.add(menu.getKey());
			}
		}
		//只有名稱加進list
		List<Menu> orderInfoByName = orderDao.findAllById(orderMenuFindPrice);
		//原有菜單, 點餐菜單一一拿出來比較名稱
		for (Menu item : orderInfoByName) {
			for (Entry<String, Integer> menu : orderMap.entrySet()) {
				//判斷名稱相同
				if (item.getItem().equals(menu.getKey())) {
					//                  菜單價格       *   點餐份數
					int orderPrice = item.getPrice() * menu.getValue();
					totalPrice += orderPrice;
					orderMapInMenu.put(item.getItem(), orderPrice);
				}
			}
		}
		if(totalPrice > 500) {
			totalPrice *= 0.9;
		}
		
		return new OrderResponse(orderInfoByName, orderMapInMenu, totalPrice, "成功");
	}
	

	@Override
	public OrderResponse orderHomeworkByT(Map<String, Integer> orderMap) {
		//最終點餐Map(實際存在原始菜單內的餐), (Value)Integer份數
		Map<String, Integer> finalOrderMap = new HashMap<>();
		//最後總價格
		int finalTotalPrice = 0;
		//將點餐從點餐單orderMap內拿出
		for(Entry<String, Integer> item : orderMap.entrySet()) {
			//做判斷份數小於0 > 出錯
			if(item.getValue() < 0) {
				return new OrderResponse("餐點數量錯誤");
			}
			//用容器裝 用Map的key找到之所有資訊
			Optional<Menu> op = orderDao.findById(item.getKey());
			//判斷// 判斷沒有值(包括null=沒值) 跳出for迴圈
			if(!op.isPresent()) { //若null/沒東西回傳true > + ! 相反
				continue;
			}
			//將點餐及份數放進最終點餐Map
			finalOrderMap.put(item.getKey(), item.getValue());
			//最後總價運算
			finalTotalPrice += op.get().getPrice() * item.getValue();
		}
		//判斷最後總價
		finalTotalPrice = finalTotalPrice > 500 ? (int)(finalTotalPrice * 0.9) : finalTotalPrice;
		//印出成功點餐之菜單, 最後總價, 訊息
		return new OrderResponse(finalOrderMap, finalTotalPrice, "點餐成功");
	}

	
	
//	@Override
//	public OrderResponse updateMenuPrice(List<Menu> menus) {
//		if(!CollectionUtils.isEmpty(menus)) {
//			return new OrderResponse("請輸入欲更改之餐點");
//		}
//		 List<Menu> item = orderDao.findAll(); //item(原有菜單)
//		 List<Menu> changeMenu = menusItem.;
//		for(Menu menuItem : item) {
//			int oldPrice = menuItem.getPrice();
//			for(Menu menusItem : menus) { //menusItem(想改菜單)
//				if(menuItem.getItem().equals(menusItem.getItem())) {
//					List<Menu> update = orderDao.saveAll(changeMenu);
//					return new OrderResponse(update);
//				}
//			}
//		}
//		return new OrderResponse("修改餐點成功");
//	}

}
