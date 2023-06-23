package com.example.storetablereservation.store.entity;

import com.example.storetablereservation.users.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String storeName;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Users user;

    @Column
    private String storeLocation;

    @Column
    private String storeDetail;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private int rate;

}

