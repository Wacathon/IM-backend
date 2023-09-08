package com.namecard.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private ApiInfo loginApiInfo() {
        return new ApiInfoBuilder().title("Hexagonal Namecard API")
                .description("Hexagonal Namecard API Docs").build();
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    @Bean
    public Docket authApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("인증")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.auth"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    public Docket usersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("회원")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.member"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    public Docket tagApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("태그")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.tag"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    public Docket indicatorApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("평가 정보")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.indicator"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    public Docket questionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("사용자 설정 질문")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.question"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    public Docket feedbackApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("피드백")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.feedback"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    @Bean
    public Docket searchApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("검색")
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(accessToken(), refreshToken()))
                .apiInfo(this.loginApiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.namecard.search"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(accessTokenAuth())
                .securityReferences(refreshTokenAuth())
                .build();
    }

    private List<SecurityReference> accessTokenAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("AccessToken", authorizationScopes));
    }

    private List<SecurityReference> refreshTokenAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("RefreshToken", authorizationScopes));
    }

    private ApiKey accessToken() {
        return new ApiKey("AccessToken", "X-AUTH-TOKEN", "header");
    }

    private ApiKey refreshToken() {
        return new ApiKey("RefreshToken", "X-REFRESH-TOKEN", "header");
    }
}