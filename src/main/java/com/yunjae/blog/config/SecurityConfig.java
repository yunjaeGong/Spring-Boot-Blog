package com.yunjae.blog.config;

import com.yunjae.blog.config.auth.PrincipalDetailService;
import com.yunjae.blog.jwt.JwtAuthenticationFilter;
import com.yunjae.blog.jwt.JwtAuthorizationFilter;
import com.yunjae.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration // 빈 등록
@EnableWebSecurity // Security 필터로 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    private final UserRepository userRepository;

    private final PrincipalDetailService principalDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); // 세션 정보 변경 시 Authentication 정보 변경 위한 Manager Bean
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security가 로그인 정보를 가로채며 로그인 요청을 할 때
    // password가 어떤 방법으로 hash가 되어 있는지 알아야 같은 방법으로 hashing 해 DB 값과 비교 가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  로그인을 할 때 가로챈 정보(principalDetail)을 가져와 password를 처리함
        auth.userDetailsService(principalDetailService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Configuration
    @Order(1)
    public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/api/**")
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilter(corsFilter) // @CrossOrigin(인증 필요x 경우), 시큐리티 필터에 등록(인증O)
                    .addFilter(new JwtAuthenticationFilter(authenticationManagerBean()))
                    .addFilter(new JwtAuthorizationFilter(authenticationManagerBean(), userRepository))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                    .antMatchers("/api/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                    .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().permitAll();
        }
    }

    @Configuration
    @Order(2)
    public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable() // TODO: csrf토큰 활성화
                    .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/static/**", "/board/**", "/h2-console/**")
                    .permitAll() // /auth/* 경로는 누구나 접근 가능
                    .anyRequest()
                    .authenticated()
                    .and() // 인증이 필요한 페이지 접근할 때 loginForm으로 이동
                    .formLogin()
                    .loginPage("/auth/loginForm")
                    .loginProcessingUrl("/auth/login") // spring security가 해당 주소로 오는 요청을 가로채 대신 로그인 요청 -> PrincipalDetailService
                    .defaultSuccessUrl("/"); // 정상적으로 처리되면 main 페이지로 (세션 정보 존재)
        }
    }

}
