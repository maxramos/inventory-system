package com.maxaramos.inventorysystem.controller.ws;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

	@GetMapping("/userinfo")
	public Authentication userinfo(Authentication authentication) {
		return authentication;
	}

}
