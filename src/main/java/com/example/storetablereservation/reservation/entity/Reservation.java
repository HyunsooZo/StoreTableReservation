package com.example.storetablereservation.reservation.entity;


import com.example.storetablereservation.reservation.model.Status;
import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Users user;

    @ManyToOne
    @JoinColumn
    private Store store;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime reservationTime;

    @Column
    private Boolean checkInYn;

    @Column
    private Status status;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime confirmDate;

    @Column
    private LocalDateTime acceptedDate;


}
