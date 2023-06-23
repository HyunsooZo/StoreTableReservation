package com.example.storetablereservation.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;


@UtilityClass
public class PasswordUtil {


    //비밀번호 검증용
    public static boolean isPasswordValid(String password, String encPW){
        try{
            return BCrypt.checkpw(password, encPW);
        }catch (Exception e){
            return false;
        }
    }
}
