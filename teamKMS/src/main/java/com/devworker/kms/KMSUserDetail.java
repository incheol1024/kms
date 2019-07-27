package com.devworker.kms;

import com.devworker.kms.dto.UserDto;
import com.devworker.kms.entity.UserDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import software.amazon.awssdk.services.s3.model.Grant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KMSUserDetail implements UserDetails {
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    KMSUserDetail(String user, String password){
        this.userName = user;
        this.password = password;
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("USER"));
        this.authorities = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
