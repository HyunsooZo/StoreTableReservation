package com.example.storetablereservation.users.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersInput {

    @Size(min=1, max=5 , message = "이름은 최대 5자까지 입력해야 합니다.")
    @NotBlank(message = "이름 항목은 필수입니다.")
    private String userName;

    @Email(message = "올바른 이메일형식이 아닙니다.")
    @NotBlank(message = "이메일 항목은 필수입니다.")
    private String email;

    @Size(min=4, max=10 , message = "비밀번호는 4~10자 사이로 입력해야 합니다.")
    @NotBlank(message = "비밀번호 항목은 필수입니다.")
    private String password;

    @Size(min=9, max=11 , message = "전화번호는 9~11자 사이로 입력해야 합니다.")
    @NotBlank(message = "전화번호 항목은 필수입니다.")
    private String phone;

    @NotBlank(message = "고객유형 항목은 필수입니다.")
    private UserType userType;

    @NotNull(message = "위치정보 항목은 필수입니다.")
    private Double latitude;

    @NotNull(message = "위치정보 항목은 필수입니다.")
    private Double Longitude;

}
