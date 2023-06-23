package com.example.storetablereservation.review.service;

import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.reservation.entity.Reservation;
import com.example.storetablereservation.reservation.repository.ReservationRepository;
import com.example.storetablereservation.review.entity.Review;
import com.example.storetablereservation.review.model.ReviewInput;
import com.example.storetablereservation.review.repository.ReviewRepository;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.store.repository.StoreRepository;
import com.example.storetablereservation.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    //리뷰 등록 시 별점 계산을 위해 사용되는 프라이빗 함수
    private int getAverageRateOfTheStore(int rate, Store store){
        Optional<List<Review>> optionalReview = reviewRepository.findByStore(store);
        if(!optionalReview.isPresent()){
            return rate;
        }
        List<Review> list = optionalReview.get();
        int averageRate = rate;
        int numberOfRates = 0;

        for(Review r : list){
            averageRate += r.getRate();
            numberOfRates++;
        }
        return numberOfRates==0? rate: (int)averageRate/numberOfRates;
    }

    @Override
    public ServiceResult reviewUpdate(Users user , Long id , ReviewInput reviewInput) {
        Optional<Reservation> optionalReservation =
                reservationRepository.findById(id);
        if(!optionalReservation.isPresent()){
            return ServiceResult.fail("유효하지 않은 예약입니다.");
        }
        Reservation reservation = optionalReservation.get();
        int rate = getAverageRateOfTheStore(reviewInput.getRate(), reservation.getStore());

        reviewRepository.save(
                Review.builder()
                .user(user)
                .store(reservation.getStore())
                .rate(rate)
                .comment(reviewInput.getComment())
                .regDate(LocalDateTime.now())
                .build()
        );
        return ServiceResult.success("리뷰등록이 완료되었습니다.");
    }

    @Override
    public ServiceResult getStoreReviews(Long id) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        if(!optionalStore.isPresent()){
            return ServiceResult.fail("존재하지 않는 매장입니다.");
        }
        Store store = optionalStore.get();
        Optional<List<Review>> optionalReviewList = reviewRepository.findByStore(store);
        if(!optionalReviewList.isPresent()){
            return ServiceResult.fail("리뷰가 존재하지 않습니다.");
        }
        List<Review> reviewList = optionalReviewList.get();

        return ServiceResult.success(reviewList);
    }
}