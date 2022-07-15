package ru.the_boring_developers.api.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.the_boring_developers.auth.annotation.UserId;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.OffsetDateTime;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public Docket orders() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enableUrlTemplating(true)
                .groupName("smart")
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.the_boring_developers.api.controller"))
                .paths(paths())
                .build()
                .directModelSubstitute(OffsetDateTime.class, Long.class)
                .ignoredParameterTypes(UserId.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API Smart",
                "",
                "1.0.0-SNAPSHOT",
                "",
                new Contact("", "", ""),
                "",
                "",
                Collections.emptyList());
    }

    private Predicate<String> paths() {
        return Predicates.or(regex("/.*"));
    }
}

