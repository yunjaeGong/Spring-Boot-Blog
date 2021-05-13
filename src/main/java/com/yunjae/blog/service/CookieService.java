package com.yunjae.blog.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.yunjae.blog.jwt.JwtProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Service
public class CookieService {

    public Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME/1000);
        cookie.setComment("JWT Authorization & Authentication");

        return cookie;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName) throws Exception{
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            throw new Exception("getCookie: cookie does not exist");
        }
        for (Cookie cookie : cookies) {
            System.out.println("CookieService: " + cookie.getName());
            if (cookie.getName().equals(cookieName))
                return cookie;
        }
        throw new Exception("getCookie: " + cookieName + "cookie does not exist");
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = JWT.decode(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public Map<String, Claim> extractAllClaims(String token){
        return JWT.decode(token).getClaims();
    }
}
