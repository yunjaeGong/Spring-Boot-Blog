package com.yunjae.blog.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtFormLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtFormLoginFilter(AuthenticationManager authenticationManager, String loginProcessingUrl) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl(loginProcessingUrl);
        // /api/~ 통한 로그인 처리 위해 setFilterProcessesUrl(String filterProcessesUrl)
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtFormLoginFilter: 로그인 시도");
        return super.attemptAuthentication(request, response);
    }
}
