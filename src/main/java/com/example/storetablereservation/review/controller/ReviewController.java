package com.example.storetablereservation.review.controller;


import com.example.storetablereservation.common.exception.InvalidLoginException;
import com.example.storetablereservation.common.exception.ReviewException;
import com.example.storetablereservation.common.model.ResponseError;
import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.review.model.ReviewInput;
import com.example.storetablereservation.review.service.ReviewService;
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
public class ReviewController {
    private final UsersService usersService;
    private final ReviewService reviewService;

    //검증에러 발생 시 에러 처리 (테스트 완료)
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

    //리뷰 올리기 (테스트완료)
    @PostMapping("/api/review/store/{id}")
    public ResponseEntity<?> postReview(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody @Valid ReviewInput reviewInput,
            Errors errors){

        errorValidation(errors);

        Users user = usersService.getUserFromToken(token);
        ServiceResult result = reviewService.reviewPost(user, id, reviewInput);

        return ResponseResult.result(result.getMessage());
    }


    //리뷰 조회하기
    @GetMapping("/api/review/store/{id}")
    public ResponseEntity<?> StoreReviews(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token){

        usersService.getUserFromToken(token);
        ServiceResult result = reviewService.getStoreReviews(id);

        return ResponseResult.success(result.getObject());
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<String> ReviewExceptionHandler(ReviewException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
