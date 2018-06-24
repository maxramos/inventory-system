package com.maxaramos.inventorysystem.security;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.maxaramos.inventorysystem.model.Role;

@EnableWebSecurity
public class WebSecurityConfig {

	@Value("${spring.security.user.name}")
	private String username;

	@Value("${spring.security.user.password}")
	private String password;

	@Value("${spring.security.user.roles}")
	private String[] roles;

	@Bean
	public UserDetailsService userDetailsService() throws Exception {
//		@SuppressWarnings("deprecation")
//		UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//		userDetailsManager.createUser(userBuilder.username(username).password(password).roles(roles).build());
		userDetailsManager.createUser(new User(username, "{noop}" + password, Stream.of(roles).map(Role::new).collect(Collectors.toSet())));
		return userDetailsManager;
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
		private LoginSuccessHandler loginSuccessHandler;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/login").permitAll()
					.anyRequest().authenticated()
					.and()
				.oauth2Login()
					.loginPage("/login")
					.successHandler(loginSuccessHandler)
					.and()
				.formLogin()
					.loginPage("/login")
					.successHandler(loginSuccessHandler)
					.and()
				.csrf().disable();
		}

	}

}
