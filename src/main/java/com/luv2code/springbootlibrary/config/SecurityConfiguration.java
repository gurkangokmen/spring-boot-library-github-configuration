package com.luv2code.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.Customizer;

//
// For trial: https://jwt.io/
//

@Configuration // Indicates that this class provides configuration for the application.
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable Cross Site Request Forgery
        http.csrf(AbstractHttpConfigurer::disable);

        // Protect endpoints at /api/<type>/secure

        // .authorizeHttpRequests(configurer -> ...): Configures authorization rules for specific endpoints.
        // Endpoints under "/api/books/secure/", "/api/reviews/secure/", "/api/messages/secure/", and "/api/admin/secure/" require authentication (authenticated()), while any other request is permitted (anyRequest().permitAll()).
        // .oauth2ResourceServer(oauth2ResourceServer -> ...): Configures OAuth2 resource server settings.
        // .jwt(Customizer.withDefaults()): Specifies that JSON Web Token (JWT) should be used for authentication, and applies default configurations.
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/api/books/secure/**",
                                        "/api/reviews/secure/**",
                                        "/api/messages/secure/**",
                                        "/api/admin/secure/**")
                                .authenticated()
                                .anyRequest()
                                .permitAll()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(Customizer.withDefaults()));

        // Add CORS filters

        // http.cors(Customizer.withDefaults()): Adds default Cross-Origin Resource Sharing (CORS) configuration.
        http.cors(Customizer.withDefaults());

        // Add content negotiation strategy

        // http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy()): Sets a content negotiation strategy based on request headers.
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401's to make the response friendly

        // Okta.configureResourceServer401ResponseBody(http): Configures Okta-specific settings for handling 401 (Unauthorized) responses.
        Okta.configureResourceServer401ResponseBody(http);

        //
        // SUMMARY
        //

        // Overall, this configuration sets up security rules, authentication, and authorization settings for specific endpoints in the application,
        // leveraging Okta for OAuth2 and JWT-based authentication.
        // It also includes configurations for CORS and content negotiation.
        return http.build();
    }

}









