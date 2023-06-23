package com.example.storetablereservation.review.controller;


import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.review.model.ReviewInput;
import com.example.storetablereservation.review.service.ReviewService;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final UsersService usersService;
    private final ReviewService reviewService;
    @PostMapping("/api/review/store/{id}")
    public ResponseEntity<?> postReview(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody ReviewInput reviewInput){

        Users user = usersService.getUserFromToken(token);
        ServiceResult result = reviewService.reviewUpdate(user, id, reviewInput);

        return ResponseResult.result(result.getMessage());
    }

    @GetMapping("/api/review/store/{id}/")
    public ResponseEntity<?> reviewList(
            @PathVariable Long id,
            @RequestHeader String token){

        usersService.getUserFromToken(token);
        ServiceResult result = reviewService.getStoreReviews(id);

        return ResponseResult.success(result.getObject());
    }
}
