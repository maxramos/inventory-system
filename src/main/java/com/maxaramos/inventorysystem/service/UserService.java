package com.maxaramos.inventorysystem.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maxaramos.inventorysystem.dao.UserDao;
import com.maxaramos.inventorysystem.model.User;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!userDao.existsByUsername(username)) {
			throw new UsernameNotFoundException("Username: " + username);
		}

		return userDao.findByUsername(username);
	}

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
