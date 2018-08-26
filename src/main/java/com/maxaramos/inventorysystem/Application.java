package com.maxaramos.inventorysystem;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.maxaramos.inventorysystem.model.Item;
import com.maxaramos.inventorysystem.model.Product;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.maxaramos.inventorysystem.dao")
@EntityScan(basePackages = "com.maxaramos.inventorysystem.model")
public class Application {

	@Autowired
	private Logger log;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Scope("prototype")
	public Logger log(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}

	@Bean
	@Scope("prototype")
	public Item item() {
		log.info("Getting Item");
		Instant now = Instant.now();
		Product product = product();
		log.info(product.toString());
		return new Item("" + now.toEpochMilli(), now.toEpochMilli());
	}

	@Bean
	public Product product() {
		log.info("Getting Product");
		Instant now = Instant.now();
		return new Product("" + now.toEpochMilli());
	}

}
