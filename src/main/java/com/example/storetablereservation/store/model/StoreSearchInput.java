package com.example.storetablereservation.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreSearchInput {
    private String storeName;
    private OrderBy orderBy;
}
