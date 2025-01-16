package com.example.ACboard.config;

import com.example.ACboard.filter.JwtAuthFilter;
import com.example.ACboard.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private JwtProvider jwtProvider;
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security
            ,JwtProvider jwtProvider,UserDetailsServiceImpl userDetailsService) throws Exception {

        String list[]={"/","/WEB-INF/views/**","/error","/favicon.ico","/resources/**","/user/login"
        ,"/user/register"};
        security
                .csrf(AbstractHttpConfigurer :: disable)
                .httpBasic(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(request->
                        request
                                .requestMatchers(list).permitAll()
                                .anyRequest().authenticated()
                        )
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthFilter(userDetailsService,jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .formLogin(login->
                        login
                                .loginPage("/user/login")
                                .loginProcessingUrl("/user/login2")
                                .defaultSuccessUrl("/",true)
                                .permitAll()
                        )
        ;
        return security.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
