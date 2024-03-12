package com.ecommerce.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Size(min = 3, max = 15, message = "3~15글자를 입력해주세요")
    private String firstName;

    @Size(min = 3, max = 15, message = "3~15글자를 입력해주세요")
    private String lastName;

    private String username;

    @Size(min = 5, max = 20, message = "비밀번호는 5~20글자여야 합니다")
    private String password;

    private String repeatPassword;
}
