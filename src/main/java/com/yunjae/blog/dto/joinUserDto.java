package com.yunjae.blog.dto;

import com.yunjae.blog.model.User;
import com.yunjae.blog.validation.ValidationProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class joinUserDto implements ValidationProperties{
    @Size(min = 1, max = USERNAME_LENGTH)
    @NotBlank()
    private String username;

    @Size(min = 8, max = PASSWORD_LENGTH)
    @NotBlank()
    private String password;

    @Size(min = 8, max = EMAIL_LENGTH)
    @NotBlank()
    private String email;

    public User toEntity() {
        return User.builder().username(username)
                .password(password)
                .email(email)
                .build();
    }
}
