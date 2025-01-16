package com.bit.security.config;

import com.bit.security.service.OAuthService;
import com.bit.security.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// 이 클래스는 설정 클래스로써, 스프링이 구동될때 자동으로 호출된다.
@Configuration
// 웹 주소마다 접근 가능 여부를 시큐리티를 통해서 처리한다.
@EnableWebSecurity
// 메소드마다 접근 가능 여부를 시큐리티를 통해서 처리한다.
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private OAuthService oAuthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, UserDetailsServiceImpl userDetailsServiceImpl ) throws Exception {
        // csrf : Cross Site Request Forgery
        //  교차 사이트 요청 위조
        // 브라우저에서 보낸 요처와
        // 돌아온 응답의 프로토콜, 도메인, 포트번호가
        // 동일한 때에만 정상 동작한다. 아니면 에러
        // 하지만 우리가 나중에 resufulApi 서버까지 도입하게 되면
        // 에러가 발생하기 때문에 cors를 꺼주기도한다.
        security
                .csrf(AbstractHttpConfigurer :: disable)
                .cors(AbstractHttpConfigurer :: disable)
                // 접근 권한 설정하기
                .authorizeHttpRequests(request ->
                                request
                                        .requestMatchers("/user/oAuth2/kakao").permitAll()
                                        // " / " 누구든 접근 가능
                                        .requestMatchers("/").permitAll()
                                        .requestMatchers("/WEB-INF/views/**").permitAll()
                                         .requestMatchers("/user/auth").permitAll()
                                        .requestMatchers("/error","/favicon.ico","/resources/**").permitAll()

                                        .requestMatchers("/board/write").hasRole("ADMIN")
                                        // /admin/** 은 관리자만 접근 가능하다.
                                        .requestMatchers("/board/**").authenticated()
                                        // hasRoleAny("ADMIN)
                                        // /board/ 는 로그인한 유저만 접근 가능하다.

                                        .requestMatchers("/upload/").permitAll()

                                        .requestMatchers("/admin/**").hasAuthority("ADMIN")


                                        // 위에 설정 되지 않은 모든 요청은
                                        // 전부 로그인된 유저만 볼수 있다.
                                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                // 로그인 시 어느 URL 접근할지 지정
                                .loginPage("/")
                                // 로그인 성공적이면 어느 URL로 이동할지
                                .defaultSuccessUrl("/board/upload", true)
                                .loginProcessingUrl("/user/auth")

                )
                .rememberMe(config->

                        // 로그인 시 리멤버 미 옵션이 어떤 이름으로 들어올지 지정
                        // 만약 따로 지정하지 않으면 remember-me 라는 이름으로 자동지정된다.
                        config.rememberMeParameter("remember-me")
                                .tokenValiditySeconds(86400*30)
                                .userDetailsService(userDetailsServiceImpl)
                                // 리멤버 미를 통해서 로그인을 성공했을 때
                                // 어떠한 액션을 취하지를
                                // LogInSuccessHandler 객체를 통해서 해결하낟.
                               // .authenticationSuccessHandler(loginSuccessHandler())
                                // 로그인을 성공한 사용자가 토큰을 저장할 때 어떤 이름으로 저장하고
                                // 리멤버 미가 정상 작동할 때 어떠한 이름의 키를 받아올지 지정해주자.
                                .key("bit_seucirty")
                        )
                .logout(logout->logout
                        .logoutUrl("/user/logout")
                        .deleteCookies("remember-me")
                        .addLogoutHandler(
                                ((request, response, authentication) -> {
                                    HttpSession session=request.getSession();
                                    // 현재 사용자가 접속한 세션을 무효화 시킨다.
                                    if(session != null){
                                        session.invalidate();
                                    }
                                })
                        )
                        // 로그아웃이 성공하면 어떠한 행동을 할 것인지를 담당하는
                        // 로그아웃 성공 핸들러 만들어주기
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            // 로그아웃이 성공하면 인덱스 화면으로 돌아간다.
                            response.sendRedirect("/");
                        }))

                )
        ;

        security.oauth2Login(oauth->
                oauth
                        .loginPage("/")
                        .loginProcessingUrl("/user/oAuth2/kakao")
                        .defaultSuccessUrl("/board/upload")
                        .userInfoEndpoint(userInfo->
                                userInfo
                                        .userService(oAuthService))
        );
        return security.build();
    }

}
