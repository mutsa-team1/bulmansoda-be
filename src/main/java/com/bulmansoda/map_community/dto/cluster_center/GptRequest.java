package com.bulmansoda.map_community.dto.cluster_center;

import lombok.Data;
import java.util.List;

/**
 * AI 서버에 클러스터링을 요청하기 위한 DTO 클래스
 */
public class GptRequest {

    /**
     * AI가 분석할 새로운 개인 마커 정보
     */
    @Data
    public static class MarkerForAI {
        private double latitude;
        private double longitude;
        private String content;
    }

    /**
     * AI에게 제공할 기존 통합 마커(클러스터)의 요약 정보
     */
    @Data
    public static class CenterMarkerForAI {
        private Long id;              // 통합마커 pk
        private double latitude;      // 현재 중심 위도
        private double longitude;     // 현재 중심 경도
        private List<String> keywords;  // 3개의 핵심 요약 키워드
    }
}
