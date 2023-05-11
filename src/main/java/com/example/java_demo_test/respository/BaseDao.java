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
	
	@PersistenceContext //JPA專有的注釋
	private EntityManager entityManager;
	
	//protected或public都可 但是private不行       方法名稱(  語法, 參數, 回傳型態     )  Query : 查詢
//	@SuppressWarnings({ "unchecked", "rawtypes" }) // > add@suppressWarnings 在方法doQuery上(一般加在外面) > 32~34行
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz){ //class不能定義名稱 > clazz = class
		//建立query  EntityType > 泛型
//		Query query = entityManager.createQuery(sql, clazz); //>import java.persistence
//		if(!CollectionUtils.isEmpty(params)) {
//			//用Foreach從Map拿東西 set進去
//			for(Entry<String, Object> item : params.entrySet()) {
//				query.setParameter(item.getKey(), item.getValue());
//			}
////		=27~30
////			for(Parameter p : query.getParameters()) {
////				query.setParameter(p, params.get(p.getName()));
////			}
////			做完弄19行
//		}
//		return query.getResultList(); //型態(22行)是List > add @suppressWarnings(21行)
		return doQuery(sql, params, clazz, -1);
	}
	/*
	 * 限制回傳筆數
	 */
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz, int limitSize){ //class不能定義名稱 > clazz = class
		//建立query  EntityType > 泛型
		Query query = entityManager.createQuery(sql, clazz); //>import java.persistence
		//用Foreach從Map拿東西 set進去
		if(!CollectionUtils.isEmpty(params)) {
			//用Foreach從Map拿東西 set進去
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
	 * limitSize : 限制回傳筆數
	 * startPosition : 每頁起始位置
	 */
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params,
			Class<EntityType> clazz, int limitSize, int startPosition){ //class不能定義名稱 > clazz = class
		//建立query  EntityType > 泛型
		Query query = entityManager.createQuery(sql, clazz); //>import java.persistence
		//用Foreach從Map拿東西 set進去
		if(!CollectionUtils.isEmpty(params)) {
			//用Foreach從Map拿東西 set進去
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
			Class<EntityType> clazz, int limitSize, int startPosition){ //class不能定義名稱 > clazz = class
		//建立query  EntityType > 泛型
		Query query = entityManager.createNativeQuery(sql, clazz); //>import java.persistence
		//用Foreach從Map拿東西 set進去
		if(!CollectionUtils.isEmpty(params)) {
			//用Foreach從Map拿東西 set進去
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
