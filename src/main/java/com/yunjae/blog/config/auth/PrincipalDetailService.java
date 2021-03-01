package com.yunjae.blog.config.auth;

import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, username, password를 가로채는데 password 값은 알아서 처리.
    // DB에서 username 존재만 확인하면 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자는 찾을 수 없습니다 : " + username);
                });
        return new PrincipalDetail(principal); // security session에 유저 정보 저장, 전달 (필요 타입: userDetails)
    }
}
