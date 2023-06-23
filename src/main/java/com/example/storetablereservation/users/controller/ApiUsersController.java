package com.example.storetablereservation.users.controller;

import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.store.service.StoreService;
import com.example.storetablereservation.users.model.UserLoginInput;
import com.example.storetablereservation.users.model.UsersInput;
import com.example.storetablereservation.users.repository.UsersRepository;
import com.example.storetablereservation.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ApiUsersController {
    private final UsersService usersService;
    private final StoreService storeService;
    private final UsersRepository usersRepository;


    @PostMapping("/api/user/registration")
    public ResponseEntity<?> registration(
            @RequestBody @Valid UsersInput usersInput) {
        ServiceResult result = usersService.userRegistration(usersInput);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> createToken(
            @RequestBody @Valid UserLoginInput userLoginInput, Errors errors) {
        ServiceResult result = usersService.userLogin(userLoginInput, errors);
        // 토큰 반환
        return ResponseResult.result(result);
    }
}
