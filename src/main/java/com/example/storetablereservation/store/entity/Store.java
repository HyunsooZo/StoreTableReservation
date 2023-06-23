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
    @NotBlank(message = "상점이름 항목은 필수입니다.")
    private String storeName;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Users user;

    @Column
    @NotBlank(message = "위치 항목은 필수입니다.")
    private String storeLocation;

    @Column
    @NotBlank(message = "상점설명 항목은 필수입니다.")
    private String storeDetail;

    @Column
    @NotBlank(message = "위치 항목은 필수입니다.")
    private double latitude;

    @Column
    @NotBlank(message = "위치 항목은 필수입니다.")
    private double longitude;

    @Column
    @Nullable
    private int rate;

}

