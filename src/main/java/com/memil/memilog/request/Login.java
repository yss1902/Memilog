package com.memil.memilog.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Login {

    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    @NotBlank(message = "패스워드를 입력하세요")
    private String password;
}
