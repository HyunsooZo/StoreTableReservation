package com.example.storetablereservation.store.controller;


import com.example.storetablereservation.common.exception.InvalidLoginException;
import com.example.storetablereservation.common.exception.StoreRegistrationException;
import com.example.storetablereservation.common.model.ResponseError;
import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.store.model.StoreInput;
import com.example.storetablereservation.store.model.StoreSearchInput;
import com.example.storetablereservation.store.service.StoreService;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiStoreController {
    private final StoreService storeService;
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

    //매장 정보 등록 (점주용) (테스트 완료)
    @PostMapping("/api/store/registration")
    public ResponseEntity<?> storeRegistration(
            @RequestHeader("STORE-TOKEN") String token
            , @RequestBody @Valid StoreInput storeInput
            ,Errors errors) {

        ResponseEntity<?> responseErrorList1 = errorValidation(errors);
        if (responseErrorList1 != null) return responseErrorList1;

        Users user = usersService.getUserFromToken(token);

        //토큰검증 및 점주인지 확인 한 후 store 에 저장
        ServiceResult result = storeService.registerStore(user, storeInput);

        return ResponseResult.result(result);
    }


    //매장 전체 리스트 조회 (테스트 완료)
    @GetMapping("/api/store/list")
    public ResponseEntity<?> getStoreList(
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody StoreSearchInput storeSearchInput) {

        //토큰 검증 후 리스트 가져오기
        Users user = usersService.getUserFromToken(token);
        ServiceResult storeList = storeService.getStoreList(user, storeSearchInput);

        return ResponseResult.result(storeList.getObject());
    }


    //매장 상세정보 조회(고객) (테스트 완료)
    @GetMapping("/api/store/{id}/detail")
    public ResponseEntity<?> storeDetail(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {

        //토큰 & 유저 검증
        usersService.getUserFromToken(token);
        ServiceResult storeDetail = storeService.getStoreDetail(id);

        if(storeDetail.isFail()){
            return ResponseResult.fail(storeDetail.getMessage());
        }

        return ResponseResult.result(storeDetail.getObject());
    }


    @ExceptionHandler(StoreRegistrationException.class)
    public ResponseEntity<String> StoreRegistrationExceptionHandler(StoreRegistrationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
