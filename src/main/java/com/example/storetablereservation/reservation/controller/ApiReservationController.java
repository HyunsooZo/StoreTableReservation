package com.example.storetablereservation.reservation.controller;


import com.example.storetablereservation.common.exception.ReservationException;
import com.example.storetablereservation.common.model.ResponseResult;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.reservation.model.ReservationInput;
import com.example.storetablereservation.reservation.model.ReservationListInput;
import com.example.storetablereservation.reservation.service.ReservationService;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApiReservationController {
    private final ReservationService reservationService;
    private final UsersService usersService;

    //예약 생성 (테스트 완료)
    @PostMapping("/api/reservation/store/{id}")
    public ResponseEntity<?> makeReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody ReservationInput reservationInput) {

        Users user = usersService.getUserFromToken(token);
        ServiceResult result =
                reservationService.makeAReservation(id, user, reservationInput);

        return ResponseResult.result(result.getObject());
    }


    //매장 방문 후 예약 확인 (테스트 완료)
    @PatchMapping("/api/reservation/store/{id}/checkin")
    public ResponseEntity<?> confirmReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {
        Users user = usersService.getUserFromToken(token);
        ServiceResult result = reservationService.reservationConfirm(user, id);

        return ResponseResult.result(result.getObject());
    }

    //날짜별 예약목록 조회 (점주) (테스트 완료)
    @GetMapping("/api/reservation/list")
    public ResponseEntity<?> getReservationList(
            @RequestHeader("STORE-TOKEN") String token,
            @RequestBody ReservationListInput reservationListInput) {

        Users user = usersService.getUserFromToken(token);

        ServiceResult result = reservationService.listReservation(user, reservationListInput);

        return ResponseResult.result(result.getObject());
    }

    //점주가 예약 승인/거절 (테스트 완료)
    @PatchMapping("/api/reservation/{id}/approval")
    public ResponseEntity<?> approveReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {
        Users user = usersService.getUserFromToken(token);

        ServiceResult result = reservationService.reservationApproval(id, user);

        return ResponseResult.result(result.getObject());
    }

    //(거절 테스트 완료)
    @PatchMapping("/api/reservation/{id}/disapproval")
    public ResponseEntity<?> disapproveReservation(
            @PathVariable Long id,
            @RequestHeader("STORE-TOKEN") String token) {
        Users user = usersService.getUserFromToken(token);

        ServiceResult result = reservationService.reservationDisapproval(id, user);

        return ResponseResult.result(result.getObject());
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> ReservationExceptionHandler(ReservationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}