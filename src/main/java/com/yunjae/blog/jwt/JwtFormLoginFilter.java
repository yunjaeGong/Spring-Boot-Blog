package com.yunjae.blog.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.UserRepository;
import com.yunjae.blog.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

public class JwtFormLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final CookieService cookieService;
    private final UserRepository userRepository;

    public JwtFormLoginFilter(AuthenticationManager authenticationManager, String loginProcessingUrl, UserRepository userRepository, CookieService cookieService) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl(loginProcessingUrl);
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        // /api/~ 통한 로그인 처리 위해 setFilterProcessesUrl(String filterProcessesUrl)
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("formLogin: 로그인 시도");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // PrincipalDetail principalDetails = (PrincipalDetail) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("testToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                // .withClaim("id", principalDetails.getUser().getId())
                // .withClaim("username", principalDetails.getUser().getUsername())
                .withClaim("id", 1)
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        Cookie accessToken = cookieService.createCookie(JwtProperties.HEADER_STRING, jwtToken);

        response.addHeader(JwtProperties.HEADER_STRING, jwtToken);
        response.addCookie(accessToken);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
