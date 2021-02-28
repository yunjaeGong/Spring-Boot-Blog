package com.yunjae.blog.test;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptionTest {
    @Test
    public void passwordHashEncryption() {
        String encString =  new BCryptPasswordEncoder().encode("1234");
        System.out.println(encString);

    }
}
