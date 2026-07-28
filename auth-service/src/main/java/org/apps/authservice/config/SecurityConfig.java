package org.apps.authservice.config;

import lombok.RequiredArgsConstructor;
import org.apps.authservice.security.CustomOAuth2UserService;
import org.apps.authservice.security.OAuth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(request -> {

                    CorsConfiguration configuration =
                            new CorsConfiguration();

                    configuration.setAllowedOrigins(
                            List.of("http://localhost:5173")
                    );

                    configuration.setAllowedMethods(
                            List.of("*")
                    );

                    configuration.setAllowedHeaders(
                            List.of("*")
                    );

                    return configuration;
                }))

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .anyRequest()

                        .permitAll()
                )

                .oauth2Login(oauth ->

                        oauth

                                .userInfoEndpoint(userInfo ->

                                        userInfo.userService(
                                                customOAuth2UserService
                                        )
                                )

                                .successHandler(
                                        successHandler
                                )
                );

        return http.build();
    }
}
