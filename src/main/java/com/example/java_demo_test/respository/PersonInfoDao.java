package com.example.java_demo_test.respository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.PersonInfo;

@Repository // �U�޸�Ʈw
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {
	
	@PersistenceContext //JPA�M�����`��
//	private EntityManager entityManager;
	
	public List<PersonInfo> findByAge(int age);
	
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);
	
	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int fromAge, int toAge);
	
	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int age1, int age2);
	
	public List<PersonInfo> findByCityContaining(String str);
	
	public List<PersonInfo> doQueryByAge(int age);
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize);
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize, int startPosition);
	
	public List<PersonInfo> findByNameOrCity(String name, String city);
	
	@Transactional
	public int UpdateAgeByName(int age, String name);
	
//	                         �O�W > set > p.id = :(���঳�Ů�)�۩w�q�W�r...
	@Transactional
	@Modifying
	@Query("update PersonInfo p set p.name = :newName where p.id = :newId")
	public int updateNameById(@Param("newId") String inputId,
			 @Param("newName") String inputName); //���\1����0
	
	@Transactional
	@Modifying
	@Query(value = "select * from person_info p  "
			+ "where p.name regexp :inputName or p.city regexp :inputCity", nativeQuery = true)
	public List<PersonInfo> QueryByNameOrCity(@Param("inputName") String name, 
			@Param("inputCity") String city);
	
}
