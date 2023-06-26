package com.example.storetablereservation.review.service;

import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.review.model.ReviewInput;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.users.entity.Users;

public interface ReviewService {

    //리뷰 작성 (별점, 코멘트)
    ServiceResult reviewPost(Users user, Long id, ReviewInput reviewInput);

    //스토어의 평균 리뷰점수, 각 유저들이 남긴 리뷰(코멘트,별점)리스트로 가져오는 함수
    ServiceResult getStoreReviews(Long id);

    //리뷰 등록 시 별점 계산을 위해 사용되는함수 (2번 함수 내부에서 호출)
    int getAverageRateOfTheStore(int rate, Store store);
}
