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
		//���b:�ˬd�Ѽ�
		//�ˬdlist�O�_��null �� ��
		if(CollectionUtils.isEmpty(personInfoList)) {
			return new PersonInfoResponse("�s�W��T���~");
		}
		List<String> ids = new ArrayList<>();
		//�ˬdlist�����C����T(PersonInfo)
		for(PersonInfo item : personInfoList) {
//			//id(String)�ˬd���o��null, �Ŧr��, �ť�
//			if(!StringUtils.hasText(item.getId())) {
//				return new PersonInfoResponse("�s�W��T���~");
//			}
//			//name(String)���o��null, �Ŧr��, �ť�
//			if(!StringUtils.hasText(item.getName())) {
//				return new PersonInfoResponse("�W�r��T���~");
//			}
//			//age(int)���o���t��
//			if(item.getAge() < 0) {
//				return new PersonInfoResponse("�~�ָ�T���~");
//			}
//			//city(String)���o��null, �Ŧr��, �ť�
//			if(!StringUtils.hasText(item.getCity())) {
//				return new PersonInfoResponse("���y��T���~");
//			}
			//�ˬdid(String), name(String), city(String)���o��null, �Ŧr��, �ť�, age(int)���o���t��
			if(!StringUtils.hasText(item.getId()) || !StringUtils.hasText(item.getName())
					|| item.getAge() < 0 || !StringUtils.hasText(item.getCity())) {
				return new PersonInfoResponse("�s�W��T���~");
			}
			//�`��id
			ids.add(item.getId());
		}
		//�ˬd�n�s�W��ids�O�_�w�s�b > ��ids���ƮwDao��, �Y���h�[�ires list(�u���w�g�s�b)
		List<PersonInfo> res = personInfoDao.findAllById(ids);
		
		//����list�P���[�Jlist����Y���׬ۦP����[�Jlist����T�ҭ��� > �L�X��T
		if(res.size() == personInfoList.size()) {
			return new PersonInfoResponse("�s�W��T����");
		}
		
		//�Yres�����ת�ܸ�ƮwDao���w���ۦP���
		if(res.size() > 0) {
//			return new PersonInfoResponse("�s�W��T����");
			//�N���Ƹ�ƪ�id�s�ilist(�u��id)
			List<String> resIds = new ArrayList<>();
			for(PersonInfo item : res) {
				resIds.add(item.getId());
			}
			//�N�����Ƹ�Ʀs�ilist(��s�W��ƻP���Ƹ��(�u��id)���id, �Yfalse�h�[�i�����Ƹ��list)
			List<PersonInfo> saveList = new ArrayList<>();
			for(PersonInfo item : personInfoList) {
				if(!resIds.contains(item.getId())) { //contains�P�_���L�]�t�Y����
					saveList.add(item);
				}
			}
//			=77.78
//			List<PersonInfo> saveList2 = personInfoList.stream()
//					.filter(item -> resIds.contains(item.getId())).collect(Collectors.toList());
			
			//�����Ƹ��list�[�i��Ʈw�æL�X��T
			personInfoDao.saveAll(saveList);
			return new PersonInfoResponse(saveList, "�s�W��T���\");
		}
		//�Y<0��ܭ�s�W��Ƥ��õL���Ƹ��, �����[�i��Ʈw
		personInfoDao.saveAll(personInfoList);
		return new PersonInfoResponse(personInfoList, "�s�W��T���\");
	}

	@Override
	public GetPersonInfoResponse getPersonInfoById(String id) {
		//���b
		if(!StringUtils.hasText(id)) {
			return new GetPersonInfoResponse("id���o����");
		}
		Optional<PersonInfo> op = personInfoDao.findById(id);
		if(!op.isPresent()) {
			return new GetPersonInfoResponse("��Ƥ��s�b");
		}
		return new GetPersonInfoResponse(op.get(), "���o��Ʀ��\");
	}

	@Override
	public GetPersonInfoResponse getAllPersonInfo() {
		return new GetPersonInfoResponse(personInfoDao.findAll(), "���o��Ʀ��\");
	}

	
	
	
	
	
}
