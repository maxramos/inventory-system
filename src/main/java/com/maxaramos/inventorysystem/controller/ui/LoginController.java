package com.maxaramos.inventorysystem.controller.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

	@Autowired
	private InMemoryClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	@ModelAttribute(name = "clientRegistrationList")
	public List<ClientRegistration> getClientRegistrationList() {
		List<ClientRegistration> clientRegistrationList = new ArrayList<>();
		clientRegistrationRepository.iterator().forEachRemaining(clientRegistrationList::add);
		clientRegistrationList.sort(Comparator.comparing(ClientRegistration::getClientName));
		return clientRegistrationList;
	}

}
