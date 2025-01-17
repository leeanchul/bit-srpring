package com.study.ACboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception {
        String[] list = {"/","WEB-INF/views/**", "/resources/**", "/index"
                , "/error", "/favicon.ico", "/resources/**","/user/**",};
        hs
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer:: disable)
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/","/WEB-INF/views/**", "/resources/**","/error", "/favicon.ico","/user/register").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form->
                        form
                                .loginProcessingUrl("/user/login")
                                .loginPage("/user/login")
                                .permitAll()
                )
                ;
        return hs.build();
    }
}
