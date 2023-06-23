package com.example.storetablereservation.reservation.repository;

import com.example.storetablereservation.reservation.entity.Reservation;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Optional<Reservation> findByUser(Users user);

    //일별 예약목록 조회 (파트너)
    Optional<List<Reservation>> findByStoreAndReservationTimeBetween(Store store, LocalDateTime startDate, LocalDateTime endDate);
}
