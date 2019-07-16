package com.ssh.syk.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.syk.dao.UserDao;

@Service("userService")
@Transactional
public class UserService {
	@Resource(name = "userDao")
	private UserDao userDao;
	
	public Map<String, Object> register(String name, String password) {
		return userDao.register(name, password);
	}
	
	public Map<String, Object> login(String name, String password) {
		return userDao.login(name, password);
	}
}
