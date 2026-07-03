package org.apps.QuantityMeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

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
                            List.of("*")
                    );

                    configuration.setAllowedMethods(
                            List.of(
                                    "GET",
                                    "POST",
                                    "PUT",
                                    "DELETE",
                                    "OPTIONS"
                            )
                    );

                    configuration.setAllowedHeaders(
                            List.of("*")
                    );

                    return configuration;
                }))

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/h2-console/**"
                        ).permitAll()

                        .anyRequest()

                        .permitAll()
                )

                .headers(headers ->
                        headers.frameOptions(
                                frame -> frame.disable()
                        )
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}