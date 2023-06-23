package com.example.storetablereservation.common.model;

import org.springframework.http.ResponseEntity;

public class ResponseResult {
    public static ResponseEntity<?> success() {
        return success(null);
    }
    public static ResponseEntity<?> fail(String message) {
        return ResponseEntity.badRequest().body(ResponseMessage.fail(message));
    }

    public static ResponseEntity<?> result(ServiceResult result) {
        if(result.isFail()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success(result.getMessage());
    }

    public static ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }

    public static ResponseEntity<?> result(Object data) {
        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }


}