package com.example.storetablereservation.users.entity;

import com.example.storetablereservation.users.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "이름 항목은 필수입니다.")
    private String userName;

    @Column
    @NotBlank(message = "이메일 항목은 필수입니다.")
    private String email;

    @Column
    @NotBlank(message = "비밀번호 항목은 필수입니다.")
    private String password;

    @Column
    @NotBlank(message = "전화번호 항목은 필수입니다.")
    private String phone;

    //UserType(Enum) 으로 고객과 점주 , 관리자를 구분한다.
    @Column
    @NotBlank(message = "고객유형 항목은 필수입니다.")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    @NotBlank(message = "위치 항목은 필수입니다.")
    private double latitude;

    @Column
    @NotBlank(message = "위치 항목은 필수입니다.")
    private double Longitude;

}

