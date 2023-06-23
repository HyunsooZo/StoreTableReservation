package com.example.storetablereservation.store.controller;


import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.store.model.StoreInput;
import com.example.storetablereservation.store.model.StoreSearchInput;
import com.example.storetablereservation.store.service.StoreService;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ApiStoreController {
    private final StoreService storeService;
    private final UsersService usersService;

    //매장 정보 등록 (점주용)
    @PostMapping("/api/store/registration")
    public ResponseEntity<?> storeRegistration(
            @RequestHeader("STORE-TOKEN") String token
            , @RequestBody @Valid StoreInput storeInput) {

        Users user = usersService.getUserFromToken(token);

        //토큰검증 및 점주인지 확인 한 후 store 에 저장
        ServiceResult result = storeService.registerStore(user, storeInput);

        return ResponseResult.result(result);
    }


    //매장 전체 리스트 조회
    @GetMapping("/api/store/list")
    public ResponseEntity<?> getStoreList(
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody StoreSearchInput storeSearchInput) {

        //토큰 검증 후 리스트 가져오기
        Users user = usersService.getUserFromToken(token);
        ServiceResult storeList = storeService.getStoreList(user, storeSearchInput);

        return ResponseResult.result(storeList.getObject());
    }


    //매장 상세정보 조회(고객)
    @GetMapping("/api/store/{id}/detail")
    public ResponseEntity<?> storeDetail(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {

        //토큰 & 유저 검증
        usersService.getUserFromToken(token);
        ServiceResult storeDetail = storeService.getStoreDetail(id);

        return ResponseResult.result(storeDetail.getObject());
    }

}
