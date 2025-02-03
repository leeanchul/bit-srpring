package com.example.moviebackend.provider;

import com.example.moviebackend.model.TokenDTO;
import com.example.moviebackend.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private Key key;
    // JWT 서명을 위한 key 객체

    public JwtProvider(@Value("${jwt.secret}") String secret){
        byte[] bytes= Decoders.BASE64.decode(secret);
        this.key= Keys.hmacShaKeyFor(bytes);
    }
    // JWT 생성하기
    public TokenDTO generate(UserDTO userDTO){
        TokenDTO tokenDTO= new TokenDTO();

        StringBuilder builder=new StringBuilder();

        for(GrantedAuthority a: userDTO.getAuthorities()){
            builder.append(a.getAuthority());
        }

        String authorities=userDTO.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining("."));

        Long userId=(long)userDTO.getId();
        String nickname=userDTO.getNickname();
        long now =new Date().getTime();
        Date expDate = new Date(now + 86400 * 1000 * 20);
        String token= Jwts.builder()
                .claim("username",userDTO.getUsername())
                .claim("userId",userId)
                .claim("nickname",nickname)
                .claim("authorities",authorities)
                .setExpiration(expDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        tokenDTO.setType("Bearer");
        tokenDTO.setValue(token);

        return tokenDTO;
    }

    // JWT 안에서 claim 정보를 추출하는 메소드
    private Claims getClaims(String token){
        try {
            // 실행할 코드
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 예외 처리
            return e.getClaims();
        }
    }
    // 토큰에서 사용자이름을 추출한다.
    public String getUsername(String token){
        return getClaims(token).get("username",String.class);
    }

    // JWT 복호화
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (claims.get("authorities") == null) {
            throw new RuntimeException("NO Authorities Info Included");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(
                                claims.get("authorities").toString().split("."))

                        .map(SimpleGrantedAuthority::new)
                        .toList();

        UserDTO userDTO=new UserDTO();

        Collection<GrantedAuthority> authorList=new ArrayList<>();
        for(GrantedAuthority a : authorities){
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(a.getAuthority());
            authorList.add(authority);
        }
        userDTO.setAuthorities(authorList);
        userDTO.setUsername(claims.getSubject());
        userDTO.setNickname((String)claims.get("nickname"));
        userDTO.setId((Integer) claims.get("userId"));

        return new UsernamePasswordAuthenticationToken(userDTO,"",authorities);
    }

    // jwt 검증
    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //  HTTP 요청에서 JWT 토큰을 추출하는 메소드입니다
    public String readToken(HttpServletRequest request){
        String token= request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer")){
            return token.substring(7);
        }

        return null;
    }

}
