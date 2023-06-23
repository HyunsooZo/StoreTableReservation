package com.example.storetablereservation.reservation.controller;


import com.example.storetablereservation.common.exception.ReservationException;
import com.example.storetablereservation.common.exception.StoreRegistrationException;
import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.reservation.model.ReservationInput;
import com.example.storetablereservation.reservation.model.ReservationListInput;
import com.example.storetablereservation.reservation.service.ReservationService;
import com.example.storetablereservation.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class ApiReservationController {
    private final ReservationService reservationService;

    //예약 생성
    @PostMapping("/api/reservation/store/{id}")
    public ResponseEntity<?> makeReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody ReservationInput reservationInput) {

        Users user = reservationService.getUserFromToken(token);
        ServiceResult result =
                reservationService.makeAReservation(id, user, reservationInput);

        return ResponseResult.result(result.getObject());
    }


    //매장 방문 후 예약 확인
    @PatchMapping("/api/reservation/checkin")
    public ResponseEntity<?> confirmReservation(
            @RequestHeader("STORE-TOKEN") String token) {
        Users user = reservationService.getUserFromToken(token);
        ServiceResult result = reservationService.reservationConfirm(user);

        return ResponseResult.result(result.getObject());
    }

    //날짜별 예약목록 조회 (점주)
    @GetMapping("/api/reservation/list")
    public ResponseEntity<?> getReservationList(
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody ReservationListInput reservationListInput){

        Users user = reservationService.getUserFromToken(token);

        ServiceResult result = reservationService.listReservation(user,reservationListInput);

        return ResponseResult.result(result.getObject());
    }

    //점주가 예약 승인/거절
    @PatchMapping("/api/reservation/{id}/approval")
    public ResponseEntity<?> approveReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {
        Users user = reservationService.getUserFromToken(token);

        ServiceResult result = reservationService.reservationApproval(id,user);

        return ResponseResult.result(result.getObject());
    }

    @PatchMapping("/api/reservation/{id}/disapproval")
    public ResponseEntity<?> disapproveReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {
        Users user = reservationService.getUserFromToken(token);

        ServiceResult result = reservationService.reservationDisapproval(id,user);

        return ResponseResult.result(result.getObject());
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> ReservationExceptionHandler(ReservationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}