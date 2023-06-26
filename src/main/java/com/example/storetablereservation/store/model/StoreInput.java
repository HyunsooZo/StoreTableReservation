package com.example.storetablereservation.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInput {
    @NotBlank(message = "상점이름은 필수입력항목입니다.")
    private String storeName;

    @NotBlank(message = "상점주소는 필수입력항목입니다.")
    private String storeAddress;

    @NotBlank(message = "상점상세정보는 필수입력항목입니다.")
    private String storeDetail;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

}