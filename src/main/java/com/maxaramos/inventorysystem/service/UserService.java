package com.maxaramos.inventorysystem.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxaramos.inventorysystem.dao.UserDao;
import com.maxaramos.inventorysystem.model.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;

	public void save(User user) {
		userDao.save(user);
	}

	public boolean existsByUsername(String username) {
		return userDao.existsByUsername(username);
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
