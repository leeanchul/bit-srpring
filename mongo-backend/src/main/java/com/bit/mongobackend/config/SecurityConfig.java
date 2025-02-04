package com.bit.mongobackend.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter JWT_FILTER;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        security.csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(req->{
                    CorsConfiguration config=new CorsConfiguration();
                    config.addAllowedOrigin("http://localhost:3000");
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");

                    return config;
                }))
                .authorizeHttpRequests(req->
                        req
                                .requestMatchers("/api/user/**").permitAll()
                                .requestMatchers("/api/board/**","/api/reply/**").authenticated()


                ).addFilterBefore(JWT_FILTER, UsernamePasswordAuthenticationFilter.class)
        ;

        return security.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
