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
		// ��J���O���O��
		for (Menu menusName : menus) {
			if (menusName.getItem() == null) {
				System.out.println("�\�I�W�ٿ��~");
				return;
			}
		}
		// �\�I�W�٭���
		for (int i = 0; i < menus.size() - 1; i++) {
			for (int j = i + 1; j < menus.size(); j++) {
				// �qlist���X�� ���X�Ӫ��F����\�I�W�r ������e
				if (menus.get(i).getItem().equals(menus.get(j).getItem())) {
					System.out.println("!�\�I�W�٭���!");
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
			// �\�I���椣�o���s�ά��t
			if (menus.get(i).getPrice() <= 0) {
				System.out.println("!�\�I������~!");
				return;
			}
		}
	}

	@Override
	public OrderResponse addMenus(List<Menu> menus) {
		// �Y�}�C���� �h���|�ifor��������97�x�s�æL�s�W���\
		if (CollectionUtils.isEmpty(menus)) { // =menus == null || menus.isEmpty()
			return new OrderResponse("�s�W�\�I���~");
		}
		for (Menu item : menus) {
			// �\�I�W�٤��O�ť�or�Ŧr�� hasText(���F��)>�ΤϦV(!)
			if (!StringUtils.hasText(item.getItem())) {
				return new OrderResponse("�\�I�W�ٿ��~");
			}
			if (item.getPrice() <= 0) {
				return new OrderResponse("�\�I������~");
			}
		}
		// list, set�h��>saveAll/ map�浧
		List<Menu> response = orderDao.saveAll(menus);
		return new OrderResponse(response, "�s�W�\�I���\");
//	=	OrderResponse res = new OrderResponse();
//		res.setMenus(response);
//		res.setMessage("�s�W�\�I���\");
//		return res;
	}

	@Override
	public OrderResponse order(Map<String, Integer> orderMap) {
		List<String> itemList = new ArrayList<>();
		for (Entry<String, Integer> item : orderMap.entrySet()) { // map��foreach(�\�I�W��, �ƶq)
			if (item.getValue() < 0) {
				return new OrderResponse("�\�I�ƶq���~");
			}
			itemList.add(item.getKey());
		}
		List<Menu> result = orderDao.findAllById(itemList); // ���I�\���Ҧ��\�I
		int totalPrice = 0;
		Map<String, Integer> finalOrderMap = new HashMap<>();
		for (Menu menu : result) {
			String item = menu.getItem();
			int price = menu.getPrice();
			for (Entry<String, Integer> map : orderMap.entrySet()) {
				String key = map.getKey(); // orderMap�̪��\�I�W��
				int value = map.getValue(); // orderMap�̪��\�I�ƶq
				if (item.equals(key)) {
					int singleTotalPrice = price * value; // ����*�ƶq
					totalPrice += singleTotalPrice;
					finalOrderMap.put(key, value); // ���ڤW���s�b���\�I
				}
			}
		}
		totalPrice = totalPrice > 500 ? (int) (totalPrice * 0.9) : totalPrice;
		return new OrderResponse(finalOrderMap, totalPrice, "�I�\���\");
	}

	@Override
	public OrderResponse getAllMenus() {
		return new OrderResponse(orderDao.findAll(), "���o�\�I���\");
	}

	@Override
	public GetMenuResponse getMenuByName(String name) {
		if (!StringUtils.hasText(name)) {
			return new GetMenuResponse("�п�J�����\�I�W��");
		}
		Optional<Menu> op = orderDao.findById(name); // Optional�ߤ@����
		if (op.isEmpty()) {
			return new GetMenuResponse("���o�\�I����");
		}
		return new GetMenuResponse(op.get(), "���o��榨�\");
	}

//	=getMenuByName
	public GetMenuResponse getMenuByItem(String name) {
		if (!StringUtils.hasText(name)) {
			return new GetMenuResponse("�п�J�����\�I�W��");
		}
		Menu menu = orderDao.findByItem(name);
		if (menu == null) {
			return new GetMenuResponse("���o�\�I����");
		}
		return new GetMenuResponse(menu, "���\");
	}

	@Override
	public OrderResponse updateMenuPrice(List<Menu> menus) { // beef:150; AAA:50; tea:60
		if (CollectionUtils.isEmpty(menus)) {
			return new OrderResponse("�����~");
		}
		// �N��J���[�Jlist
		List<String> ids = new ArrayList<>();
		for (Menu menu : menus) {

			if (menu.getPrice() < 0) {
				return new OrderResponse("��������~");
			}
			ids.add(menu.getItem()); // �u�s�W��ids=["beef", "AAA", "tea"]
		}
		List<Menu> res = orderDao.findAllById(ids); // res(in DB) = beef:120; tea:30
		if (ids.size() == 0) {
			return new OrderResponse("�d�L���");
		}
		List<Menu> newMenuList = new ArrayList<>();
		for (Menu resMenu : res) {
			String itemInDB = resMenu.getItem();
			for (Menu menu : menus) { // �쥻�����
				String updateItem = menu.getItem();
				if (itemInDB.equals(updateItem)) {
					newMenuList.add(menu);
				}
			}
		}
		return new OrderResponse(orderDao.saveAll(newMenuList), "��s���\");
	}

	@Override
	public OrderResponse orderHomework(Map<String, Integer> orderMap) {
		// �I���\�I(�u��key > ���A��String) > �n�h�����
		List<String> orderMenuFindPrice = new ArrayList<>();
		//int>��~�`��
		Map<String, Integer> orderMapInMenu= new HashMap<>(); 
		int totalPrice = 0;
		//�I�\��orderMap���F�讳�X�ӥ[�imenu list
		for (Entry<String, Integer> menu : orderMap.entrySet()) {
			//        �P�_��Ʈw���ۦP���W(�u��key)>�[�ilist
			if (orderDao.existsById(menu.getKey())) {
				orderMenuFindPrice.add(menu.getKey());
			}
		}
		//�u���W�٥[�ilist
		List<Menu> orderInfoByName = orderDao.findAllById(orderMenuFindPrice);
		//�즳���, �I�\���@�@���X�Ӥ���W��
		for (Menu item : orderInfoByName) {
			for (Entry<String, Integer> menu : orderMap.entrySet()) {
				//�P�_�W�٬ۦP
				if (item.getItem().equals(menu.getKey())) {
					//                  ������       *   �I�\����
					int orderPrice = item.getPrice() * menu.getValue();
					totalPrice += orderPrice;
					orderMapInMenu.put(item.getItem(), orderPrice);
				}
			}
		}
		if(totalPrice > 500) {
			totalPrice *= 0.9;
		}
		
		return new OrderResponse(orderInfoByName, orderMapInMenu, totalPrice, "���\");
	}
	

	@Override
	public OrderResponse orderHomeworkByT(Map<String, Integer> orderMap) {
		//�̲��I�\Map(��ڦs�b��l��椺���\), (Value)Integer����
		Map<String, Integer> finalOrderMap = new HashMap<>();
		//�̫��`����
		int finalTotalPrice = 0;
		//�N�I�\�q�I�\��orderMap�����X
		for(Entry<String, Integer> item : orderMap.entrySet()) {
			//���P�_���Ƥp��0 > �X��
			if(item.getValue() < 0) {
				return new OrderResponse("�\�I�ƶq���~");
			}
			//�ήe���� ��Map��key��줧�Ҧ���T
			Optional<Menu> op = orderDao.findById(item.getKey());
			//�P�_// �P�_�S����(�]�Anull=�S��) ���Xfor�j��
			if(!op.isPresent()) { //�Ynull/�S�F��^��true > + ! �ۤ�
				continue;
			}
			//�N�I�\�Υ��Ʃ�i�̲��I�\Map
			finalOrderMap.put(item.getKey(), item.getValue());
			//�̫��`���B��
			finalTotalPrice += op.get().getPrice() * item.getValue();
		}
		//�P�_�̫��`��
		finalTotalPrice = finalTotalPrice > 500 ? (int)(finalTotalPrice * 0.9) : finalTotalPrice;
		//�L�X���\�I�\�����, �̫��`��, �T��
		return new OrderResponse(finalOrderMap, finalTotalPrice, "�I�\���\");
	}

	
	
//	@Override
//	public OrderResponse updateMenuPrice(List<Menu> menus) {
//		if(!CollectionUtils.isEmpty(menus)) {
//			return new OrderResponse("�п�J����蠟�\�I");
//		}
//		 List<Menu> item = orderDao.findAll(); //item(�즳���)
//		 List<Menu> changeMenu = menusItem.;
//		for(Menu menuItem : item) {
//			int oldPrice = menuItem.getPrice();
//			for(Menu menusItem : menus) { //menusItem(�Q����)
//				if(menuItem.getItem().equals(menusItem.getItem())) {
//					List<Menu> update = orderDao.saveAll(changeMenu);
//					return new OrderResponse(update);
//				}
//			}
//		}
//		return new OrderResponse("�ק��\�I���\");
//	}

}
