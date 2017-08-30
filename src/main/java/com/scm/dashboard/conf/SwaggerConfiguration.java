package com.scm.dashboard.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.enable(true)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.scm.dashboard.controller"))
			.paths(PathSelectors.any())
			.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("APIs sharing ...... ")
			.description("Welcome to Hangzhou SCM dashboard")
			.termsOfServiceUrl("http://localhost:8081")
			.contact(
					new Contact(
						"Hangzhou SCM", 
						"https://git.oschina.net/84649162/scm_dashboard", 
						"leo-leo.wang@nokia.com"
					)
				)
			.version("1.0")
			.license("The Apache License, Version 2.0")
			.build();
	}
}
