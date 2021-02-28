package com.yunjae.blog.config.auth;

import com.yunjae.blog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Spring Security가 로그인 요청을 가로채서 로그인을 요청하고, 완료되면 UserDetail 타입의 오브젝트를
// Spring Security의 고유한 세션 저장소에 저장을 해준다
public class principalDetail implements UserDetails {
    User user;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // if not expired: true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // if not locked: true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // if password not expired: true
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정 권환 목록(admin, user..) 반환 - 권한이 여러 개 있으면 loop를 돌아야 하지만 지금으로서는 하나밖에 없다.
        Collection<GrantedAuthority> collection = new ArrayList<>();
        //TODO: 계정 권한 추가되면 아래 수정
        collection.add(() -> { return "ROLE_" + user.getRole(); }); // 권한이 하나만 남아 가능
        return collection;
    }
}
