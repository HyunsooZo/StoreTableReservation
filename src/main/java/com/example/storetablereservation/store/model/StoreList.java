package com.example.storetablereservation.store.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreList implements Comparable<StoreList> {
    private String storeName;
    private int storeRate;
    private String distanceFromUser;

    @Override
    public int compareTo(StoreList o) {
        return this.storeName.compareTo(o.getStoreName());
    }
}