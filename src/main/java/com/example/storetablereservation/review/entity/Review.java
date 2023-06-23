package com.example.storetablereservation.review.entity;

import com.example.storetablereservation.store.entity.Store;
import com.example.storetablereservation.users.entity.Users;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private Store store;

    @JoinColumn
    @ManyToOne
    private Users user;

    @Column
    private String comment;

    @Column
    private int rate;

    @Column
    private LocalDateTime regDate;
}
