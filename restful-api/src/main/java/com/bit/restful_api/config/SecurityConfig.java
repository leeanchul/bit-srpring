package com.bit.restful_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(request ->
                                request
                                        .requestMatchers("/WEB-INF/views/**").permitAll()
                                         .requestMatchers("/","/user/auth").permitAll()
                                        .requestMatchers("/error","/favicon.ico","/resources/**","/upload/**").permitAll()

                                        .requestMatchers("/board/**").permitAll()
                                        .requestMatchers("/reply//**").authenticated()
                                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/")
                                .defaultSuccessUrl("/board/upload", true)
                                .loginProcessingUrl("/user/auth")
                )
        ;
        return security.build();
    }
}
