package com.example.storetablereservation.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginInput {

    @NotBlank(message = "이메일은 필수항목입니다..")
    private String email;

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String password;
}
