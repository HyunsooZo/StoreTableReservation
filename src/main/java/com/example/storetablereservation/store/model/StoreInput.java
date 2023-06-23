package com.example.storetablereservation.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInput {
    private String storeName;
    private String storeLocation;
    private String storeDetail;
    private double latitude;
    private double Longitude;
}