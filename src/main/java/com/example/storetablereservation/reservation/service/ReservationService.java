package com.example.storetablereservation.reservation.service;

import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.reservation.model.ReservationInput;
import com.example.storetablereservation.reservation.model.ReservationListInput;
import com.example.storetablereservation.users.entity.Users;

import java.time.LocalDate;

public interface ReservationService {
    

    //예약 생성용 함수
    ServiceResult makeAReservation(Long id,Users user, ReservationInput reservationInput);

    //매장 방문 후 키오스크 예약 확인용 함수
    ServiceResult reservationConfirm(Users user,Long id);

    // 예약 승인 ( 파트너 회원용 )
    ServiceResult reservationApproval(Long id, Users user);

    // 예약 거절 ( 파트너 회원용 )
    ServiceResult reservationDisapproval(Long id, Users user);

    //예약 목록 보기 ( 파트너 회원용)
    ServiceResult listReservation(Users user, ReservationListInput reservationListInput);
}
