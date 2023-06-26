package com.example.storetablereservation.users.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.storetablereservation.common.exception.InvalidLoginException;
import com.example.storetablereservation.common.exception.InvalidTokenException;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.common.util.JWTUtil;
import com.example.storetablereservation.common.util.PasswordUtil;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.model.UserLoginInput;
import com.example.storetablereservation.users.model.UserLoginToken;
import com.example.storetablereservation.users.model.UsersInput;
import com.example.storetablereservation.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    //토큰 및 유저정보 검증용 (토큰/유저정보 유효할때만 정상값 반환)
    @Override
    public Users getUserFromToken(String token) {
        String email = "";
        try {
            email = JWTUtil.getIssuer(token);
        } catch (SignatureVerificationException e) {
            throw new InvalidTokenException("토큰이 만료되었습니다. 다시 로그인 해주세요");
        } catch (Exception e){
            throw new InvalidTokenException("토큰검증에 실패했습니다. 다시 로그인 해주세요");
        }

        Optional<Users> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new InvalidTokenException("토큰이 만료되었습니다. 다시 로그인 해주세요");
        }
        return optionalUser.get();
    }



    //비번 암호화 함수
    private String getEncryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }


    @Override
    public ServiceResult userRegistration(UsersInput usersInput) {
        long existing = usersRepository.countByEmail(usersInput.getEmail());
        //db에 해당 회원 정보가 0개 이상있다면(존재한다면) 기회원임을 알리는 메세지 반환
        if (existing > 0) {
            return ServiceResult.fail("이미 가입된 회원입니다.");
        }
        String encryptPassword = getEncryptPassword(usersInput.getPassword());
        Users user = Users.builder()
                .userName(usersInput.getUserName())
                .email(usersInput.getEmail())
                .password(encryptPassword)
                .phone(usersInput.getPhone())
                .userType(usersInput.getUserType())
                .build();

        usersRepository.save(user);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult userLogin(UserLoginInput userLoginInput) {

        Users user = usersRepository.findByEmail(userLoginInput.getEmail())
                .orElseThrow(()-> new InvalidLoginException("존재하지 않는 회원 입니다."));

        if(!PasswordUtil.isPasswordValid(userLoginInput.getPassword(), user.getPassword())){
            throw new InvalidLoginException("비밀번호가 일치하지 않습니다.");
        }

        //비밀번호, 회원정보 유무 여부 확인 후 유효한 로그인 정보 일 시 토큰 정보 반환
        return ServiceResult.success(tokenIssue(user));
    }

    @Override
    public String tokenIssue(Users user) {
        //토큰발행 (유효기간 1일)
        Date expiredDate = java.sql.Timestamp.valueOf(
                LocalDateTime.now().plusHours(1));

        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("storetable".getBytes()));

        UserLoginToken.builder().token(token).build();
        return token;
    }
}
