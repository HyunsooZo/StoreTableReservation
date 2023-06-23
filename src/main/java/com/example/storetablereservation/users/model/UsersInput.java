package com.example.storetablereservation.users.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersInput {
    private String userName;
    private String email;
    private String password;
    private String phone;
    private UserType userType;
    private Double latitude;
    private Double Longitude;
}
