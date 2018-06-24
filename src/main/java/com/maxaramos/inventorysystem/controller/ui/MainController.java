package com.maxaramos.inventorysystem.controller.ui;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.maxaramos.inventorysystem.model.User;
import com.maxaramos.inventorysystem.service.UserService;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauth2Authentication = (OAuth2AuthenticationToken) authentication;
			OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(oauth2Authentication.getAuthorizedClientRegistrationId(), authentication.getName());
			model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
		}

		User user = userService.findByUsername(authentication.getName());

		if (user != null) {
			model.addAttribute("username", user.getUsername());
			model.addAttribute("password", user.getPassword());
			model.addAttribute("role", user.getAuthorities().stream().findAny().orElse(null));
		}
		return "/index";
	}

	@GetMapping("/userinfo")
	public String userinfo(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		Map<?, ?> userAttributes = Collections.emptyMap();
		String userInfoEndpointUri = authorizedClient.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

		if (!StringUtils.isEmpty(userInfoEndpointUri)) {
			userAttributes = WebClient
					.builder()
					.filter(oauth2Credentials(authorizedClient))
					.build()
					.get()
					.uri(userInfoEndpointUri)
					.retrieve()
					.bodyToMono(Map.class)
					.block();
		}

		model.addAttribute("userAttributes", userAttributes);
		return "/userinfo";
	}

	private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			ClientRequest authorizedRequest = ClientRequest
					.from(clientRequest)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizedClient.getAccessToken().getTokenValue())
					.build();
			return Mono.just(authorizedRequest);
		});
	}

}
