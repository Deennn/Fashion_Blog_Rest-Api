package com.example.fashion_blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Fashion Blog Api",
                "Fashion Blog Api Documentation",
                "1",
                "Terms of service",
                new Contact("Lawal Abideen",
                        "www.Deeenn/github",
                        "abideenlawal70@gmail.com"),
                "Licesnse Of API",
                "API license URL",
                Collections.emptyList()
        );



    }


    @Bean
    public Docket api() {

        return new Docket(
                DocumentationType.SWAGGER_2
        )
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }
}
