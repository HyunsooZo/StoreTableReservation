package com.example.storetablereservation.reservation.service;

import com.example.storetablereservation.common.exception.ReservationException;
import com.example.storetablereservation.common.model.ServiceResult;
import com.example.storetablereservation.reservation.entity.Reservation;
import com.example.storetablereservation.reservation.model.ReservationInput;
import com.example.storetablereservation.reservation.model.ReservationListInput;
import com.example.storetablereservation.reservation.model.Status;
import com.example.storetablereservation.reservation.repository.ReservationRepository;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.store.repository.StoreRepository;
import com.example.storetablereservation.users.entity.Users;
import com.example.storetablereservation.users.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.storetablereservation.reservation.model.Status.*;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    //예약 생성 함수 (테스트 완료)
    @Override
    public ServiceResult makeAReservation(Long id, Users user, ReservationInput reservationInput) {

        Optional<Store> optionalStore = storeRepository.findById(id);
        if (!optionalStore.isPresent()) {
            throw new ReservationException("존재하지 않는 매장입니다.");
        }
        Store store = optionalStore.get();

        Optional<Reservation> optionalReservation = reservationRepository.findByUser(user);
        if(optionalReservation.isPresent()){
            if(optionalReservation.get().getStatus()== WAITING
                    || optionalReservation.get().getStatus()== ACCEPTED
                    && optionalReservation.get().getStore()==store){
                throw new ReservationException("같은 매장에 이미 완료되지 않은 예약이 존재합니다.");
            }
        }

        Reservation reservation = Reservation.builder()
                .store(store)
                .user(user)
                .reservationTime(reservationInput.getReservationTime())
                .checkInYn(false)
                .regDate(LocalDateTime.now())
                .status(WAITING)
                .build();

        reservationRepository.save(reservation);

        return ServiceResult.success(reservation);
    }


    //예약 확인 함수 (테스트 완료)
    @Override
    public ServiceResult reservationConfirm(Users user, Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findByUser(user);
        if (!optionalReservation.isPresent()) {
            throw new ReservationException("예약내역이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();

        if (reservation.getStore().getId() != id) {
            throw new ReservationException("예약내역이 존재하지 않습니다.");
        }

        if (reservation.getStatus() == REJECTED) {
            throw new ReservationException("매장으로부터 거절된 예약입니다.");
        } else if (reservation.getStatus() == WAITING) {
            throw new ReservationException("매장에서 아직 승인하지 않은 예약입니다.");
        }

        if (reservation.getReservationTime().minusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new ReservationException("죄송합니다. 예약 전 10분이 지난 후에는 도착확인이 불가합니다.");
        }

        reservation.setCheckInYn(true);
        reservation.setStatus(Status.CLOSED);
        reservation.setConfirmDate(LocalDateTime.now());

        reservationRepository.save(reservation);

        return ServiceResult.success(reservation);
    }


    //예약 승인 함수 ( 테스트 완료)
    @Override
    public ServiceResult reservationApproval(Long id, Users user) {
        if (!user.getUserType().equals(UserType.PARTNER)) {
            throw new ReservationException("예약승인은 파트너회원만 가능합니다.");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (!optionalReservation.isPresent()) {
            throw new ReservationException("예약내역이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();
        reservation.setStatus(Status.ACCEPTED);
        reservation.setAcceptedDate(LocalDateTime.now());
        reservationRepository.save(reservation);

        return ServiceResult.success("예약 승인 완료.");
    }


    //예약 거절함수 ( 테스트 완료)
    @Override
    public ServiceResult reservationDisapproval(Long id, Users user) {
        if (!user.getUserType().equals(UserType.PARTNER)) {
            throw new ReservationException("예약거절은 파트너회원만 가능합니다.");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (!optionalReservation.isPresent()) {
            return ServiceResult.fail("예약내역이 존재하지 않습니다.");
        }
        Reservation reservation = optionalReservation.get();
        reservation.setStatus(REJECTED);
        reservation.setAcceptedDate(LocalDateTime.now());
        reservationRepository.save(reservation);

        return ServiceResult.success("예약 거절 완료.");
    }


    //내 매장의 예약리스트 보기 ( 파트너 회원전용 ) ( 테스트완료 )
    @Override
    public ServiceResult listReservation(Users user, ReservationListInput reservationListInput) {
        if (!user.getUserType().equals(UserType.PARTNER)) {
            throw new ReservationException("예약내역조회는 파트너회원만 가능합니다.");
        }
        Optional<Store> optionalStore = storeRepository.findByUser(user);
        if (!optionalStore.isPresent()) {
            throw new ReservationException("매장을 등록한 파트너회원만 예약내역조회가 가능합니다.");
        }
        Store store = optionalStore.get();

        Optional<List<Reservation>> optionalReservation =
                reservationRepository.findByStoreAndReservationTimeBetween(
                        store, reservationListInput.getStartDate(),
                        reservationListInput.getEndDate());

        if (!optionalReservation.isPresent()) {
            return ServiceResult.fail("예약내역이 존재하지 않습니다.");
        }

        List<Reservation> reservationList = optionalReservation.get();

        return ServiceResult.success(reservationList);
    }
}

