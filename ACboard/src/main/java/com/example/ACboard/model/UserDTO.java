package com.example.ACboard.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDTO implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private Collection<GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities == null){
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
        }
        // 로그인한 유저가 가지고 있는 권한 리스트를 리턴한다.
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }@Override
    public boolean isAccountNonExpired() {
        // 해당 계정이 만료가 안되었는가?
        // 아직 만료기간이 남았다 -> true
        // 만료되었다 -> false
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겨있는가?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 유효기간이 만료되지 않았는가?
        // 아직 유효하다 -> true
        // 만료됬다 -> false
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 사용자 계정이 사용가능한가?
        // 가능하다 -> true
        // 불가능하다 -> false
        return UserDetails.super.isEnabled();
    }
}
