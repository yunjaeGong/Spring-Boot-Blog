package com.yunjae.blog.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.yunjae.blog.config.auth.PrincipalDetail;
import com.yunjae.blog.model.User;
import com.yunjae.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtAuthorizationFilter: 인증이나 권한이 필요한 주소 요청됨");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("JwtAuthorizationFilter: Authorization error, missing Jwt Header");
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = jwtHeader.replace("Bearer ", "");

        String username =
                JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

        if (username != null) {
            User userEntity = userRepository.findByUsername(username).orElseThrow(()-> {
                try {
                    System.out.println("존재하지 않는 사용자입니다");
                    chain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new UsernameNotFoundException("해당 사용자는 찾을 수 없습니다 : " + username);
            });

            // jwt토큰 서명만을 통해 만든 객체. 서명이 정상이면 객체 생성
            PrincipalDetail principal = new PrincipalDetail(userEntity);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        return;
    }


}
