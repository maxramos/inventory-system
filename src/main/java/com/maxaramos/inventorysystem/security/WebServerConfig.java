package com.maxaramos.inventorysystem.security;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerConfig {

	@Value("${invsys.server.http.port}")
	private int httpPort;

	@Value("${server.port}")
	private int httpsPort;

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setPort(httpPort);
		connector.setRedirectPort(httpsPort);

		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityCollection securityCollection = new SecurityCollection();
				securityCollection.addPattern("/*");

				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				securityConstraint.addCollection(securityCollection);

				context.addConstraint(securityConstraint);
			}
		};
		factory.addAdditionalTomcatConnectors(connector);
		return factory;
	}

}
