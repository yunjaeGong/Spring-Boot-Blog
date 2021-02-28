package com.yunjae.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 빈 등록
@EnableWebSecurity // Security 필터로 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
             .csrf().disable() // TODO: csrf토큰 활성화
             .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "css/**", "image/**")
                .permitAll() // /auth/* 경로는 누구나 접근 가능
                .anyRequest()
                .authenticated()
             .and() // 인증이 필요한 페이지 접근할 때 loginForm으로 이동
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/login") // spring security가 해당 주소로 오는 요청을 가로채 대신 로그인 요청
                .defaultSuccessUrl("/"); // 정상적으로 처리되면 main 페이지로
    }
}
