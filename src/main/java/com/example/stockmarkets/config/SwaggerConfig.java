package com.example.stockmarkets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.stockmarkets.rest.controller")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("DowJones Indices API")
                .description("DowJones Indices API reference for developers")
                .termsOfServiceUrl("http://examples.com")
                .contact(new Contact("henry", "", "examples@gmail.com"))
                .license("Examples License")
                .licenseUrl("examples@gmail.com")
                .version("1.0")
                .build();
    }

}
