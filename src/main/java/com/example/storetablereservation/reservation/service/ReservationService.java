package com.example.storetablereservation.reservation.service;

import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.reservation.model.ReservationInput;
import com.example.storetablereservation.reservation.model.ReservationListInput;
import com.example.storetablereservation.users.entity.Users;

import java.time.LocalDate;

public interface ReservationService {
    
    //유저 토큰 검증용 함수
    Users getUserFromToken(String token);
    
    //예약 생성용 함수
    ServiceResult makeAReservation(Long id,Users user, ReservationInput reservationInput);

    //매장 방분 후 키오스트 예약 확인용 함수 
    ServiceResult reservationConfirm(Users user);

    ServiceResult reservationApproval(Long id, Users user);

    ServiceResult reservationDisapproval(Long id, Users user);

    ServiceResult listReservation(Users user, ReservationListInput reservationListInput);
}
