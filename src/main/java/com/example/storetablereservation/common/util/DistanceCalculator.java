package com.example.storetablereservation.common.util;

import java.text.DecimalFormat;

public class DistanceCalculator {
    // 지구 평균 반지름 (단위: km)
    private static final double EARTH_RADIUS = 6378.1370;

    public static String calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안 단위로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 두 좌표의 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // 평면에서 좌표 간 거리 계산
        double distance = Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon);

        // 포맷 지정
        DecimalFormat decimalFormat = new DecimalFormat("#0.000");

        // 결과 반환
        return decimalFormat.format(distance);
    }
}
