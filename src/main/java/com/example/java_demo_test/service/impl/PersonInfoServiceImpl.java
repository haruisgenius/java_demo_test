package com.example.java_demo_test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.respository.PersonInfoDao;
import com.example.java_demo_test.service.ifs.PersonInfoService;
import com.example.java_demo_test.vo.GetPersonInfoResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

@Service
public class PersonInfoServiceImpl implements PersonInfoService{
	
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public PersonInfoResponse addPersonInfo(List<PersonInfo> personInfoList) {
		//防呆:檢查參數
		//檢查list是否為null 或 空
		if(CollectionUtils.isEmpty(personInfoList)) {
			return new PersonInfoResponse("新增資訊錯誤");
		}
		List<String> ids = new ArrayList<>();
		//檢查list中的每項資訊(PersonInfo)
		for(PersonInfo item : personInfoList) {
//			//id(String)檢查不得為null, 空字串, 空白
//			if(!StringUtils.hasText(item.getId())) {
//				return new PersonInfoResponse("新增資訊錯誤");
//			}
//			//name(String)不得為null, 空字串, 空白
//			if(!StringUtils.hasText(item.getName())) {
//				return new PersonInfoResponse("名字資訊錯誤");
//			}
//			//age(int)不得為負數
//			if(item.getAge() < 0) {
//				return new PersonInfoResponse("年齡資訊錯誤");
//			}
//			//city(String)不得為null, 空字串, 空白
//			if(!StringUtils.hasText(item.getCity())) {
//				return new PersonInfoResponse("國籍資訊錯誤");
//			}
			//檢查id(String), name(String), city(String)不得為null, 空字串, 空白, age(int)不得為負數
			if(!StringUtils.hasText(item.getId()) || !StringUtils.hasText(item.getName())
					|| item.getAge() < 0 || !StringUtils.hasText(item.getCity())) {
				return new PersonInfoResponse("新增資訊錯誤");
			}
			//蒐集id
			ids.add(item.getId());
		}
		//檢查要新增的ids是否已存在 > 用ids找資料庫Dao內, 若有則加進res list(只有已經存在)
		List<PersonInfo> res = personInfoDao.findAllById(ids);
		
		//重複list與欲加入list比較若長度相同表欲加入list內資訊皆重複 > 印出資訊
		if(res.size() == personInfoList.size()) {
			return new PersonInfoResponse("新增資訊重複");
		}
		
		//若res有長度表示資料庫Dao內已有相同資料
		if(res.size() > 0) {
//			return new PersonInfoResponse("新增資訊重複");
			//將重複資料的id存進list(只有id)
			List<String> resIds = new ArrayList<>();
			for(PersonInfo item : res) {
				resIds.add(item.getId());
			}
			//將未重複資料存進list(原新增資料與重複資料(只有id)比較id, 若false則加進未重複資料list)
			List<PersonInfo> saveList = new ArrayList<>();
			for(PersonInfo item : personInfoList) {
				if(!resIds.contains(item.getId())) { //contains判斷有無包含某元素
					saveList.add(item);
				}
			}
//			=77.78
//			List<PersonInfo> saveList2 = personInfoList.stream()
//					.filter(item -> resIds.contains(item.getId())).collect(Collectors.toList());
			
			//未重複資料list加進資料庫並印出資訊
			personInfoDao.saveAll(saveList);
			return new PersonInfoResponse(saveList, "新增資訊成功");
		}
		//若<0表示原新增資料內並無重複資料, 直接加進資料庫
		personInfoDao.saveAll(personInfoList);
		return new PersonInfoResponse(personInfoList, "新增資訊成功");
	}

	@Override
	public GetPersonInfoResponse getPersonInfoById(String id) {
		//防呆
		if(!StringUtils.hasText(id)) {
			return new GetPersonInfoResponse("id不得為空");
		}
		Optional<PersonInfo> op = personInfoDao.findById(id);
		if(!op.isPresent()) {
			return new GetPersonInfoResponse("資料不存在");
		}
		return new GetPersonInfoResponse(op.get(), "取得資料成功");
	}

	@Override
	public GetPersonInfoResponse getAllPersonInfo() {
		return new GetPersonInfoResponse(personInfoDao.findAll(), "取得資料成功");
	}

	
	
	
	
	
}
