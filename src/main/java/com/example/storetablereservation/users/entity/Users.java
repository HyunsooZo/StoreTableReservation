package com.example.storetablereservation.users.entity;

import com.example.storetablereservation.users.model.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Column
    private String email;

    @JsonIgnore
    @Column
    private String password;

    @Column
    private String phone;

    //UserType(Enum) 으로 고객과 점주 , 관리자를 구분한다.
    @Column
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    private double latitude;

    @Column
    private double Longitude;

}

