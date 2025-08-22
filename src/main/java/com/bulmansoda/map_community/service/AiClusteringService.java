package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.ai.OpenAIClient;
import com.bulmansoda.map_community.ai.PromptBuilder;
import com.bulmansoda.map_community.dto.cluster_center.GptRequest;
import com.bulmansoda.map_community.dto.cluster_center.GptResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service // 서비스 로직 선언
public class AiClusteringService {

    private final OpenAIClient openAIClient; // AI API 통신 클라이언트
    private final PromptBuilder promptBuilder;   // AI 프롬프트 생성기
    private final ObjectMapper objectMapper;     // JSON <-> 객체 변환기

    // 생성자를 통한 의존성 주입
    public AiClusteringService(OpenAIClient openAIClient, PromptBuilder promptBuilder, ObjectMapper objectMapper) {
        this.openAIClient = openAIClient;
        this.promptBuilder = promptBuilder;
        this.objectMapper = objectMapper;
    }

    /**
     * 새로운 개인 마커를 기존 통합 마커에 분류하거나, 새로운 통합 마커를 생성하도록 AI에 요청
     * @param marker 새로 추가된 개인 마커
     * @param centers 현재 DB에 저장된 전체 통합 마커 리스트
     * @return AI의 판단이 담긴 GptResponse 객체
     */
    // AI 클러스터링 요청 메서드
    public GptResponse clusterOrIntegrate(GptRequest.MarkerForAI marker, List<GptRequest.CenterMarkerForAI> centers) {
        // null 방지, centers가 null이면 빈 리스트로 처리
        List<GptRequest.CenterMarkerForAI> nonNullCenters = (centers == null) ? Collections.emptyList() : centers;

        String systemPrompt = promptBuilder.system();           // AI 역할 프롬프트 생성
        String userPrompt = promptBuilder.user(marker, nonNullCenters); // 사용자 요청 프롬프트 생성
        String jsonSchema = promptBuilder.jsonSchema();         // AI 응답 형식(JSON) 정의

        // AI에 API 요청 후 JSON 응답 수신
        String jsonResponse = openAIClient.responseJson(systemPrompt, userPrompt, jsonSchema);

        try {
            // JSON 응답을 GptResponse 객체로 변환 후 반환
            return objectMapper.readValue(jsonResponse, GptResponse.class);
        } catch (Exception e) { // 예외(오류) 발생 시
            // 비상 로직 호출
            return createNewClusterFallback(marker);
        }
    }

    /**
     * AI 호출 실패 시 사용될 비상용 새 클러스터 생성 메서드
     */
    // AI 호출 실패 시 비상 메서드
    private GptResponse createNewClusterFallback(GptRequest.MarkerForAI marker) {
        GptResponse fallbackResponse = new GptResponse(); // 비상용 응답 객체 생성
        fallbackResponse.setAction("CREATE");           // 액션: "무조건 생성"

        // 좌표: 새 마커의 좌표 그대로 사용
        fallbackResponse.setLatitude(marker.getLatitude());
        fallbackResponse.setLongitude(marker.getLongitude());

        // 키워드: 내용 기반으로 간단히 생성
        String[] contentWords = marker.getContent().split(" ");
        String keyword1 = contentWords.length > 0 ? contentWords[0] : "이벤트";
        String keyword2 = contentWords.length > 1 ? contentWords[1] : "상세정보";
        String keyword3 = "확인필요"; // 고정 키워드
        fallbackResponse.setKeywords(List.of(keyword1, keyword2, keyword3));

        return fallbackResponse; // 비상용 응답 반환
    }
}
