package com.maxaramos.inventorysystem.security;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import com.maxaramos.inventorysystem.model.User;
import com.maxaramos.inventorysystem.service.UserService;

@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Configuration
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public static class WsSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/api/**")
				.authorizeRequests()
					.anyRequest().authenticated()
					.and()
				.httpBasic()
					.and()
				.csrf().disable();
		}

	}

	@Configuration
	public static class UiSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;

		@Autowired
		private UserService userService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/login").permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
					.and()
				.oauth2Login()
					.loginPage("/login")
					.successHandler(oauth2LoginSuccessHandler)
					.and()
				.formLogin()
					.loginPage("/login")
					.and()
				.headers()
					.frameOptions().sameOrigin()
					.and()
				.csrf().disable();
		}

		@Bean
		public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
			return (authorities) -> {
				Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

				authorities.forEach(authority -> {
					String username = "";

					if (OidcUserAuthority.class.isInstance(authority)) {
						OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
						OidcIdToken idToken = oidcUserAuthority.getIdToken();
						username = idToken.getClaimAsString("email");
					} else if (OAuth2UserAuthority.class.isInstance(authority)) {
						OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
						Map<String, Object> attributes = oauth2UserAuthority.getAttributes();
						username = (String) attributes.get("email");
					}

					if (userService.existsByUsername(username)) {
						User user = userService.findByUsername(username);
						mappedAuthorities.addAll(user.getAuthorities());
					} else {
						mappedAuthorities.add(authority);
					}
				});

				return mappedAuthorities;
			};
		}

	}

}




