package com.yunjae.blog.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.UserRepository;
import com.yunjae.blog.service.CookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static org.springframework.web.util.WebUtils.getCookie;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CookieService cookieService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, CookieService cookieService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.cookieService = cookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtAuthorizationFilter: 인증이나 권한이 필요한 주소 요청됨");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        Cookie jwtCookie;
        try {
            System.out.println(request.getCookies()[0].getName());
            jwtCookie = cookieService.getCookie(request, JwtProperties.HEADER_STRING);
        } catch (Exception e) {
            System.out.println("JwtAuthorizationFilter: Authorization error, missing Jwt Cookie");
            e.printStackTrace();
            chain.doFilter(request, response);
            return;
        }

        /*if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("JwtAuthorizationFilter: Authorization error, missing Jwt Header");
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }*/

        if (jwtCookie == null) {
            System.out.println("JwtAuthorizationFilter: Authorization error, missing Jwt Cookie");
            chain.doFilter(request, response);
            return;
        }

        String username = new String("");

        // String jwtToken = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
        String jwtToken = jwtCookie.getValue().replace("Bearer ", "");
        if(!jwtToken.trim().equals("")) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken);
            username = decodedJWT.getClaim("username").asString();
        }

        // username = "asdf";

        if (!username.trim().equals("")) {
            User user = userRepository.findByUsername(username).orElseThrow(()-> {
                try {
                    System.out.println("존재하지 않는 사용자입니다");
                    chain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new UsernameNotFoundException("해당 사용자는 찾을 수 없습니다");
            });

            // jwt토큰 서명만을 통해 만든 객체. 서명이 정상이면 객체 생성
            PrincipalDetail principal = new PrincipalDetail(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            // SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            System.out.println("JwtAuthorizationFilter: username not in Jwt Token");
        }
        chain.doFilter(request, response);
    }
}
