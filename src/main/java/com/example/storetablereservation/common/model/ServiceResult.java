package com.example.storetablereservation.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult {
    private boolean result;
    private String message;
    private Object object;

    public static ServiceResult fail(String message) {
        return ServiceResult.builder()
                .result(false)
                .message(message)
                .object(null)
                .build();
    }

    public static ServiceResult success() {
        return ServiceResult.builder()
                .result(true)
                .message("거래 성공")
                .object(null)
                .build();
    }

    public static ServiceResult success(String message) {
        return ServiceResult.builder()
                .result(true)
                .message(message)
                .object(null)
                .build();
    }

    public static ServiceResult success(Object object) {
        return ServiceResult.builder()
                .result(true)
                .message("거래 성공")
                .object(object)
                .build();
    }

    public boolean isFail() {
        return !result;
    }
}
