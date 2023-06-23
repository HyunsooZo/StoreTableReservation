package com.example.storetablereservation.review.service;

import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.review.model.ReviewInput;
import com.example.storetablereservation.users.entity.Users;

public interface ReviewService {

    //리뷰 작성 (별점, 코멘트)
    ServiceResult reviewUpdate(Users user, Long id , ReviewInput reviewInput);


    ServiceResult getStoreReviews(Long id);
}
