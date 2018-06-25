package com.maxaramos.inventorysystem.security;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.maxaramos.inventorysystem.model.Authority;
import com.maxaramos.inventorysystem.model.User;
import com.maxaramos.inventorysystem.service.AuthorityService;
import com.maxaramos.inventorysystem.service.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		String username = authentication.getName();

		if (userService.existsByUsername(username)) {

		} else {
			String password = (String) authentication.getCredentials();
			String authorityName = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().get();
			Authority authority = authorityService.findByAuthority(authorityName);
			Set<Authority> authorities = Stream.of(authority).collect(Collectors.toSet());
			userService.save(new User(username, password, authorities));
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
