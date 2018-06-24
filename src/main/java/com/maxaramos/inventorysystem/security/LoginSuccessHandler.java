package com.maxaramos.inventorysystem.security;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.maxaramos.inventorysystem.model.Role;
import com.maxaramos.inventorysystem.model.User;
import com.maxaramos.inventorysystem.service.UserService;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		if (!userService.existsByUsername(authentication.getName())) {
			Set<Role> roles = authentication.getAuthorities().stream().map(Role::new).collect(Collectors.toSet());
			userService.save(new User(authentication.getName(), (String) authentication.getCredentials(), roles));
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
