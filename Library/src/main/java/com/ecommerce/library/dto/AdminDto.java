package com.ecommerce.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "유효하지 않은 이름! 3~10글자를 입력해주세요")
    private String firstName;
    @Size(min = 3, max = 10, message = "유효하지 않은 이름! 3~10글자를 입력해주세요")
    private String lastName;
    private String username;
    @Size(min = 5, max = 15, message = "유효하지 않은 비밀번호! 5~15글자를 입력해주세요")
    private String password;
    private String repeatPassword;
}
