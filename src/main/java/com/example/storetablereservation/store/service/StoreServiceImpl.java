package com.example.storetablereservation.store.service;


import com.example.storetablereservation.common.exception.StoreRegistrationException;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.common.util.DistanceCalculator;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.store.model.OrderBy;
import com.example.storetablereservation.store.model.StoreInput;
import com.example.storetablereservation.store.model.StoreList;
import com.example.storetablereservation.store.model.StoreSearchInput;
import com.example.storetablereservation.store.repository.StoreRepository;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public ServiceResult registerStore(Users user, StoreInput storeInput) {
        //유저 정보 가져와서 점주인지 아닌지 확인
        if (!user.getUserType().equals(UserType.PARTNER)) {
            throw new StoreRegistrationException("상점주 회원만 상품등록을 진행할 수 있습니다.");
        }

        //이미 매장이 등록된 회원인지 확인
        Optional<Store> optionalStore = storeRepository.findByUser(user);
        if(optionalStore.isPresent()){
            throw new StoreRegistrationException("같은 계정으로 이미 등록된 매장이 존재합니다." +
                    "매장은 계정 당 한 개만 등록할 수 있습니다.");
        }

        //상점주일 경우 해당 상점 정보 저장
        storeRepository.save(Store.builder()
                .storeName(storeInput.getStoreName())
                .storeLocation(storeInput.getStoreAddress())
                .storeDetail(storeInput.getStoreDetail())
                .latitude(storeInput.getLatitude())
                .longitude(storeInput.getLongitude())
                .user(user)
                .build());

        return ServiceResult.success();
    }

    @Override
    public ServiceResult getStoreList(Users user , StoreSearchInput storeSearchInput) {

        //검색 키워드 없을 시 상점 전체목록 가져오기 , 있을 시 contains 검색 후 목록가져오기
        List<Store> listAll;
        if(storeSearchInput.equals("")){
            listAll = storeRepository.findAll();
        }else{
            listAll = storeRepository.findByStoreNameContains(storeSearchInput.getStoreName());
        }

        // 정렬하여 반환할 리스트 초기화
        List<StoreList> list = new ArrayList<>();

        //이름, 별점 , 거리 모델을 리스트에 저장하여 반환
        for (Store s : listAll) {
            String distance = DistanceCalculator.calculateDistance(
                    user.getLatitude(), user.getLongitude(),
                    s.getLatitude(), s.getLongitude()
            );

            list.add(StoreList.builder()
                    .storeName(s.getStoreName())
                    .storeRate(s.getRate())
                    .distanceFromUser(distance)
                    .build());
        }
        //정렬 기준에 맞춰 리스트 정렬하여 반환 (이름순,별점순,거리순)
        if(storeSearchInput.getOrderBy().equals(OrderBy.ALPHABET)){
            Collections.sort(list);
        }else if(storeSearchInput.getOrderBy().equals(OrderBy.RATE)){
            Collections.sort(list, Comparator.comparing(StoreList::getStoreRate).reversed());
        }else if(storeSearchInput.getOrderBy().equals(OrderBy.DISTANCE)){
            Collections.sort(list, Comparator.comparing(StoreList::getDistanceFromUser));
        }
        return ServiceResult.success(list);
    }
    @Override
    public ServiceResult getStoreDetail(Long id) {

        Optional<Store> optionalStore = storeRepository.findById(id);
        if (optionalStore.isEmpty()) {
            ServiceResult.fail("존재하지 않는 매장입니다.");
        }
        return ServiceResult.success(optionalStore.get());
    }
}
