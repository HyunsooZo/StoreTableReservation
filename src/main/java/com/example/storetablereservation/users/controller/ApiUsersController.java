package com.example.storetablereservation.users.controller;

import com.example.storetablereservation.common.exception.InvalidLoginException;
import com.example.storetablereservation.common.exception.InvalidTokenException;
import com.example.storetablereservation.common.model.ResponseError;
import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.users.model.UserLoginInput;
import com.example.storetablereservation.users.model.UsersInput;
import com.example.storetablereservation.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiUsersController {
    private final UsersService usersService;

    private ResponseEntity<?> errorValidation(Errors errors) {
        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @PostMapping("/api/user/registration")
    public ResponseEntity<?> registration(
            @RequestBody @Valid UsersInput usersInput,
            Errors errors) {

        ResponseEntity<?> responseErrorList1 = errorValidation(errors);
        if (responseErrorList1 != null) return responseErrorList1;

        ServiceResult result = usersService.userRegistration(usersInput);
        return ResponseResult.result(result);
    }

      @PostMapping("/api/user/login")
    public ResponseEntity<?> createToken(
            @RequestBody @Valid UserLoginInput userLoginInput, Errors errors) {

        ResponseEntity<?> responseErrorList = errorValidation(errors);
        if (responseErrorList != null) return responseErrorList;

        ServiceResult result = usersService.userLogin(userLoginInput);
        // 토큰 반환
        if(result.isFail()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.result(result);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> InvalidLoginExceptionHandler(InvalidLoginException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> InvalidTokenExceptionHandler(InvalidTokenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
