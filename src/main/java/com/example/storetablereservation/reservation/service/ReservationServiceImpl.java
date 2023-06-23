package com.example.storetablereservation.reservation.service;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.storetablereservation.common.exception.InvalidTokenException;
import com.example.storetablereservation.common.exception.ReservationException;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.common.util.JWTUtil;
import com.example.storetablereservation.reservation.entity.Reservation;
import com.example.storetablereservation.reservation.model.ReservationListInput;
import com.example.storetablereservation.reservation.model.Status;
import com.example.storetablereservation.reservation.model.ReservationInput;
import com.example.storetablereservation.reservation.repository.ReservationRepository;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.store.repository.StoreRepository;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.model.UserType;
import com.example.storetablereservation.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.storetablereservation.reservation.model.Status.REJECTED;
import static com.example.storetablereservation.reservation.model.Status.WAITING;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final UsersRepository usersRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;


    //토큰을 검증하는 함수.
    @Override
    public Users getUserFromToken(String token) {
        String email = "";
        try {
            email = JWTUtil.getIssuer(token);
        } catch (SignatureVerificationException e) {
            throw new InvalidTokenException("토큰 정보가 정확히지 않습니다.");
        }

        Optional<Users> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new InvalidTokenException("존재하지 않는 사용자입니다.");
        }
        return optionalUser.get();
    }


    //예약 생성 함수
    @Override
    public ServiceResult makeAReservation(Long id, Users user, ReservationInput reservationInput) {

        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            return ServiceResult.fail("존재하지 않는 매장입니다.");
        }
        Store store = optionalStore.get();
        reservationRepository.save(
                Reservation.builder()
                        .store(store)
                        .user(user)
                        .reservationTime(reservationInput.getReservationTime())
                        .checkInYn(false)
                        .regDate(LocalDateTime.now())
                        .status(WAITING)
                        .build()
        );
        Optional<Reservation> optionalReservation = reservationRepository.findByUser(user);
        if (!optionalReservation.isPresent()) {
            return ServiceResult.fail("예약에 실패하였습니다. 다시 시도 해주세요");
        }
        Reservation reservation = optionalReservation.get();
        reservationRepository.save(reservation);

        return ServiceResult.success(reservation);
    }


    //예약 확인 함수
    @Override
    public ServiceResult reservationConfirm(Users user) {
        Optional<Reservation> optionalReservation = reservationRepository.findByUser(user);
        if (!optionalReservation.isPresent()) {
            throw new ReservationException("예약내역이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();

        if(reservation.getStatus().equals(REJECTED)) {
            return ServiceResult.fail("매장으로부터 거절된 예약입니다.");
        }else if(reservation.getStatus().equals(WAITING)) {
            return ServiceResult.fail("매장에서 예약을 아직 승인하지 않았습니다.");
        }

        reservation.setCheckInYn(true);
        reservation.setConfirmDate(LocalDateTime.now());

        reservationRepository.save(reservation);

        return ServiceResult.success(reservation);
    }

    @Override
    public ServiceResult reservationApproval(Long id, Users user) {
        if(!user.getUserType().equals(UserType.STOREKEEPER)){
            throw new ReservationException("예약승인은 파트너회원만 가능합니다.");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(!optionalReservation.isPresent()){
            return ServiceResult.fail("예약내역이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();
        reservation.setStatus(Status.ACCEPTED);
        reservation.setAcceptedDate(LocalDateTime.now());
        reservationRepository.save(reservation);

        return ServiceResult.success("예약 승인 완료.");
    }
    @Override
    public ServiceResult reservationDisapproval(Long id, Users user) {
        if(!user.getUserType().equals(UserType.STOREKEEPER)){
            throw new ReservationException("예약거절은 파트너회원만 가능합니다.");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(!optionalReservation.isPresent()){
            return ServiceResult.fail("예약내역이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();
        reservation.setStatus(REJECTED);
        reservation.setAcceptedDate(LocalDateTime.now());
        reservationRepository.save(reservation);

        return ServiceResult.success("예약 거절 완료.");
    }

    @Override
    public ServiceResult listReservation(Users user, ReservationListInput reservationListInput) {
        if(!user.getUserType().equals(UserType.STOREKEEPER)){
            throw new ReservationException("예약내역조회는 파트너회원만 가능합니다.");
        }
        Optional<Store> optionalStore = storeRepository.findByUser(user);
        if(!optionalStore.isPresent()){
            throw new ReservationException("매장을 등록한 파트너회원만 예약내역조회가 가능합니다.");
        }
        Store store = optionalStore.get();

        Optional<List<Reservation>> optionalReservation=
                reservationRepository.findByStoreAndReservationTimeBetween(
                        store,reservationListInput.getStartDate(),
                        reservationListInput.getEndDate());

        if(!optionalReservation.isPresent()){
            return ServiceResult.fail("예약내역이 존재하지 않습니다.");
        }

        List<Reservation> reservationList = optionalReservation.get();

        return ServiceResult.success(reservationList);
    }
}

