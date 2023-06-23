package com.example.storetablereservation.reservation.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInput {

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime reservationTime;

}
