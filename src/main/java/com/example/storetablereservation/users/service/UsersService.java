package com.example.storetablereservation.users.service;

import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.model.UserLoginInput;
import com.example.storetablereservation.users.model.UsersInput;
import org.springframework.validation.Errors;

public interface UsersService {
    //토큰 및 유저정보 검증용 (토큰/유저정보 유효할때만 정상값 반환)
    Users getUserFromToken(String token);


    // 사용자의 이름 , 이메일, 비밀번호, 전화번호, 사용자유형(고객/점주/관리자)를 받아 회원가입 진행
    ServiceResult userRegistration(UsersInput usersInput);

    // 고객의 이메일 / 비밀번호 검증하여 토큰 발행
    ServiceResult userLogin(UserLoginInput userLoginInput);
    String tokenIssue(Users user);


}
