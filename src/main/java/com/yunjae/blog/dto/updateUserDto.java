package com.yunjae.blog.dto;

import com.yunjae.blog.model.User;
import com.yunjae.blog.validation.ValidationProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class updateUserDto implements ValidationProperties {

    @NotBlank
    private int id;

    @Size(min = 8, max = PASSWORD_LENGTH)
    @NotBlank()
    private String prevPassword;

    @Size(min = 8, max = PASSWORD_LENGTH)
    private String newPassword;

    @Size(min = 8, max = EMAIL_LENGTH)
    private String email;
}
