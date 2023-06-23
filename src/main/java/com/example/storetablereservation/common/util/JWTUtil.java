package com.example.storetablereservation.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.experimental.UtilityClass;


@UtilityClass
public class JWTUtil {

    //토큰 생성용
    private static final String KEY = "storetable";

    public static String getIssuer(String token) {
        String issuer = JWT.require(Algorithm.HMAC512(KEY.getBytes()))
                .build()
                .verify(token)
                .getIssuer();
        return issuer;
    }
}