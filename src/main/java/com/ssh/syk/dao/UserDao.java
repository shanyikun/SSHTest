package com.ssh.syk.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ssh.syk.entity.UserEntity;
import com.ssh.syk.paramPOJO.UserInfo;

@Repository("userDao")
public class UserDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	public Map<String, Object> register(String name, String password) {
		Session session = sessionFactory.openSession();
		Object isExit;
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from UserEntity where name=:name";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		isExit = query.uniqueResult();
		if(isExit==null) {
			UserEntity userEntity = new UserEntity(name, password);
			Integer id = (Integer) session.save(userEntity);
			userEntity.setId(id);
			result.put("statCode", "0");
			result.put("userEntity", userEntity);
			return result;
		}
		else {
			result.put("statCode", "500");
			result.put("message", "user hsa exists!");
			return result;
		}
	}
	
	public Map<String, Object> login(String name, String password) {
		Session session = sessionFactory.getCurrentSession();
		Object userInfo;
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from UserEntity where name=:name and password=:password";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("password", password);
		userInfo =  query.uniqueResult();
		if(userInfo!=null) {
			UserEntity userEntity = (UserEntity) userInfo;
			result.put("statCode", "0");
			result.put("userEntity", userEntity);
			return result;
		}
		else {
			result.put("statCode", "500");
			result.put("message", "user is not exit!");
			return result;
		}
	}
}
