package com.maxaramos.inventorysystem.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxaramos.inventorysystem.dao.AuthorityDao;
import com.maxaramos.inventorysystem.model.Authority;

@Service
@Transactional
public class AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;

	public Authority findByAuthority(String authority) {
		return authorityDao.findByAuthority(authority);
	}

}
