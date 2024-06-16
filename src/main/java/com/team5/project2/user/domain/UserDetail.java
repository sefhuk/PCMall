package com.team5.project2.user.domain;

import com.team5.project2.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetail implements UserDetails {

    private User user;

    public UserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() { //GrantedAuthority를 Collection안에 담기
            @Override
            public String getAuthority() {
                return user.getRole(); //여기서 역할 뽑기
            }
        });
        return collect;
    }

    //비밀번호 가져오기
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //유저 이름 가져오기
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
