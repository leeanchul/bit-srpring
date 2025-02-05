package com.example.moviebackend.config;

import com.example.moviebackend.filter.JwtAuthFilter;
import com.example.moviebackend.provider.JwtProvider;
import com.example.moviebackend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {

        hs
                .cors(cors->cors.configurationSource(req->{
                    // corsConfiguration 객체 생성
                    CorsConfiguration config=new CorsConfiguration();
                    // 프론트 로컬주소 설정
                    config.addAllowedOrigin("http://localhost:3000");
                    // get,post,put,delete 등 설정
                    config.addAllowedMethod("*");
                    // header 타입 설정
                    config.addAllowedHeader("*");

                    return config;
                }))
                .authorizeHttpRequests(req->
                        req
                                .requestMatchers("/api/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer ::disable)
                .sessionManagement(session->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthFilter(userDetailsService, jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);


                return hs.build();
    }
}
