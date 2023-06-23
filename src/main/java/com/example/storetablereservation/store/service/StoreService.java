package com.example.storetablereservation.store.service;


import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.store.model.StoreInput;
import com.example.storetablereservation.store.model.StoreSearchInput;
import com.example.storetablereservation.users.entity.Users;

public interface StoreService {

    //매장 정보 등록 ( 점주 )
    ServiceResult registerStore(Users user, StoreInput storeInput);

    //모든 매장 또는 검색키워드가 포함된 매장 리스트 검색
    ServiceResult getStoreList(Users user, StoreSearchInput storeSearchInput);

    //매장 상세정보
    ServiceResult getStoreDetail(Long id);
}
