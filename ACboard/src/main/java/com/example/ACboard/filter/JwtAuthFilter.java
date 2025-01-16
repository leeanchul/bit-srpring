package com.example.ACboard.filter;

import com.example.ACboard.config.JwtProvider;
import com.example.ACboard.model.UserDTO;
import com.example.ACboard.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private UserDetailsServiceImpl userDetailsService;
    private JwtProvider provider;
    private HttpSession session;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {

//        if(session.getAttribute("test") != null){
//            String token= (String) session.getAttribute("test");
//
//            SecurityContextHolder.getContext().setAuthentication(token);
//        }

        String header=request.getHeader("Authorization");

        if(header != null){
            String authorizationHeader=provider.readToken(request);

            if(provider.validate(authorizationHeader)){
                String username=provider.getUsername(authorizationHeader);

                Authentication authentication=provider.getAuthentication(authorizationHeader);

                UserDTO userDTO =(UserDTO) userDetailsService.loadUserByUsername(username);

                if(userDTO != null){
                    UsernamePasswordAuthenticationToken token
                            = new UsernamePasswordAuthenticationToken(userDTO,"",userDTO.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                    System.out.println(token);
                }

            }

        }
        filterChain.doFilter(request,response);
    }
}