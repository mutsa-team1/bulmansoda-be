package com.bulmansoda.map_community.dto.ai;

import lombok.Data;
import java.util.List;

public class GptRequest {
    /**
     * AI가 분석할 새로운 개인 마커 정보 */
    @Data
    public static class MarkerForAI {
        private double latitude;
        private double longitude;
        private String content;

        public MarkerForAI(double latitude, double longitude, String content) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.content = content;
        }
    }

    /**
     * AI에게 제공할 기존 통합 마커(클러스터)의 요약 정보
     */
    @Data
    public static class CenterMarkerForAI {
        private Long id;                 // 통합마커 pk
        private double latitude;         // 현재 중심 위도
        private double longitude;        // 현재 중심 경도
        private List<String> keywords;   // 3개의 핵심 요약 키워드

        public CenterMarkerForAI(Long id, double latitude, double longitude, List<String> keywords) {
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.keywords = keywords;
        }
    }

}
