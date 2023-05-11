package com.example.java_demo_test.respository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

public class BaseDao {
	
	@PersistenceContext //JPA�M�����`��
	private EntityManager entityManager;
	
	//protected��public���i ���Oprivate����       ��k�W��(  �y�k, �Ѽ�, �^�ǫ��A     )  Query : �d��
//	@SuppressWarnings({ "unchecked", "rawtypes" }) // > add@suppressWarnings �b��kdoQuery�W(�@��[�b�~��) > 32~34��
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz){ //class����w�q�W�� > clazz = class
		//�إ�query  EntityType > �x��
//		Query query = entityManager.createQuery(sql, clazz); //>import java.persistence
//		if(!CollectionUtils.isEmpty(params)) {
//			//��Foreach�qMap���F�� set�i�h
//			for(Entry<String, Object> item : params.entrySet()) {
//				query.setParameter(item.getKey(), item.getValue());
//			}
////		=27~30
////			for(Parameter p : query.getParameters()) {
////				query.setParameter(p, params.get(p.getName()));
////			}
////			������19��
//		}
//		return query.getResultList(); //���A(22��)�OList > add @suppressWarnings(21��)
		return doQuery(sql, params, clazz, -1);
	}
	/*
	 * ����^�ǵ���
	 */
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz, int limitSize){ //class����w�q�W�� > clazz = class
		//�إ�query  EntityType > �x��
		Query query = entityManager.createQuery(sql, clazz); //>import java.persistence
		//��Foreach�qMap���F�� set�i�h
		if(!CollectionUtils.isEmpty(params)) {
			//��Foreach�qMap���F�� set�i�h
			for(Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());
			}
		}
		if( limitSize > 0 ) {
			query.setMaxResults(limitSize);
		}
//		return query.getResultList(); 
		return doQuery(sql, params, clazz, limitSize, -1);
	}
	
	/*
	 * limitSize : ����^�ǵ���
	 * startPosition : �C���_�l��m
	 */
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz, int limitSize, int startPosition){ //class����w�q�W�� > clazz = class
		//�إ�query  EntityType > �x��
		Query query = entityManager.createQuery(sql, clazz); //>import java.persistence
		//��Foreach�qMap���F�� set�i�h
		if(!CollectionUtils.isEmpty(params)) {
			//��Foreach�qMap���F�� set�i�h
			for(Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());
			}
		}
		if( limitSize > 0 ) {
			query.setMaxResults(limitSize);
		}
		if(startPosition >= 0) {
			query.setFirstResult(startPosition);
		}
		return query.getResultList(); 
	}
	
	protected int doUpdate(String sql, Map<String, Object> params) {
		Query query = entityManager.createQuery(sql);
		if(!CollectionUtils.isEmpty(params)) {
			for(Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());
			}
		}
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doNativeQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz, int limitSize, int startPosition){ //class����w�q�W�� > clazz = class
		//�إ�query  EntityType > �x��
		Query query = entityManager.createNativeQuery(sql, clazz); //>import java.persistence
		//��Foreach�qMap���F�� set�i�h
		if(!CollectionUtils.isEmpty(params)) {
			//��Foreach�qMap���F�� set�i�h
			for(Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());
			}
		}
		if( limitSize > 0 ) {
			query.setMaxResults(limitSize);
		}
		if(startPosition >= 0) {
			query.setFirstResult(startPosition);
		}
		return query.getResultList(); 
	}
	
	
	
	
	
	
	
}
