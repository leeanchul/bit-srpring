package com.movie.review.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecuirtyConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception {
        hs
                .csrf(AbstractHttpConfigurer::disable)
               // .cors(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(request->
                        request
                                .requestMatchers("/","/error","/favicon.ico","/WEB-INF/views/**","/user/join","/user/join2").permitAll()
                                .requestMatchers("/movie/**").authenticated()
                                .requestMatchers("/review/**").hasRole("ROLE_REVIEWER")
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                // 로그인 시 어느 URL 접근할지 지정
                                .loginPage("/")
                                // 로그인 성공적이면 어느 URL로 이동할지
                                .defaultSuccessUrl("/movie/movieAll", true)
                                .loginProcessingUrl("/user/auth")

                )

        ;


        return hs.build();
    }
}
