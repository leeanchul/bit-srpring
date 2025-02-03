package com.bit.board_backend.security;

import com.bit.board_backend.service.UserDetailsServiceImpl;
import com.bit.board_backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil JWT_UTIL;
    private final UserDetailsServiceImpl USER_DETAILS_SERVICE;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     String token=request.getHeader("Authorization");
     if(token != null && token.startsWith("Bearer ")){
         token = token.substring(7);
         String username=JWT_UTIL.validateToken(token);
         if(username != null){
             // UserDetail 객체 생성 후 UserDetailsService 의 loadByUsername 을 통해 해당
             // username 을 가진 사용자가 있는지 체크
             // 존재하면 해당 유저를 SecurityContextHolder 에 등록
             UserDetails details = USER_DETAILS_SERVICE.loadUserByUsername(username);
             UsernamePasswordAuthenticationToken authToken=
                     new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(authToken);
         }
     }

     filterChain.doFilter(request,response);
    }
}
