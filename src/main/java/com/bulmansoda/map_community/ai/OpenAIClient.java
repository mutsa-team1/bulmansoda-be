package com.bulmansoda.map_community.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component // 스프링이 관리하는 부품으로 등록
public class OpenAIClient {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions"; // OpenAI API 엔드포인트
    private static final Logger log = LoggerFactory.getLogger(OpenAIClient.class); // 로그 기록을 위한 로거

    private final WebClient webClient; // 비동기 HTTP 통신 클라이언트
    private final ObjectMapper mapper; // JSON 처리를 위한 객체

    @Value("${openai.model}") // 설정 파일에서 모델 이름 주입
    private String model;

    // 생성자: API 키를 받아 WebClient를 초기화
    public OpenAIClient(@Value("${openai.api.key}") String apiKey, ObjectMapper mapper) {
        this.webClient = WebClient.builder()
                .baseUrl(OPENAI_API_URL) // 기본 URL 설정
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey) // 모든 요청에 인증 헤더 추가
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // 모든 요청에 콘텐츠 타입 헤더 추가
                .build();
        this.mapper = mapper;
    }

    /**
     * OpenAI Chat Completions API를 호출하여 JSON 응답을 받습니다.
     * @param system 시스템 메시지 (AI의 역할)
     * @param user 사용자 메시지 (AI의 과제)
     * @param jsonSchema AI가 반환할 JSON의 스키마
     * @return AI가 생성한 JSON 문자열. 실패 시 null 반환.
     */
    public String responseJson(String system, String user, String jsonSchema) {
        try {
            // 1. OpenAI에 보낼 요청 데이터 생성
            ChatRequest request = createChatRequest(system, user, jsonSchema);

            // 2. WebClient로 API 비동기 호출 후, 응답을 동기식으로 대기
            ChatResponse response = webClient.post() // POST 요청
                    .bodyValue(request) // 요청 본문(body) 설정
                    .retrieve() // 응답 수신
                    .bodyToMono(ChatResponse.class) // 응답 본문을 ChatResponse 객체로 변환
                    .block(); // 응답이 올 때까지 대기

            // 3. 응답 객체에서 실제 텍스트(content)만 추출
            if (response != null && !response.getChoices().isEmpty()) {
                String content = response.getChoices().get(0).getMessage().getContent();
                log.info("Successfully received AI response: {}", content); // 성공 로그
                return content; // 결과 반환
            }

            log.warn("Received an empty or invalid response from OpenAI."); // 경고 로그
            return null; // 비어있는 응답 시 null 반환

        } catch (Exception e) {
            log.error("Error calling OpenAI API: ", e); // 에러 로그
            return null; // 예외 발생 시 null 반환 -> 서비스에서 비상 로직(Fallback) 처리
        }
    }

    // OpenAI 요청 객체를 생성하는 private 헬퍼 메서드
    private ChatRequest createChatRequest(String system, String user, String jsonSchema) throws Exception {
        ChatRequest request = new ChatRequest();
        request.setModel(model); // 사용할 AI 모델 설정
        request.setResponse_format(new ResponseFormat("json_object")); // AI 응답을 JSON 객체 형식으로 강제
        request.setMessages(List.of(
                new Message("system", system), // 시스템 역할 메시지
                new Message("user", user + "\nIMPORTANT: Your output MUST strictly conform to the provided JSON schema.") // 사용자 역할 메시지 + 추가 지시
        ));
        return request;
    }

    // --- OpenAI API의 JSON 구조에 맞춘 내부 데이터 클래스(DTO)들 ---
    // --- Lombok의 @Data를 사용해 getter, setter 등 자동 생성 ---

    @Data
    private static class ChatRequest { // API 요청용 DTO
        private String model;
        private List<Message> messages;
        private ResponseFormat response_format;
    }

    @Data
    private static class Message { // 메시지 DTO
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    @Data
    private static class ResponseFormat { // 응답 형식 DTO
        private String type;

        public ResponseFormat(String type) {
            this.type = type;
        }
    }

    @Data
    private static class ChatResponse { // API 응답용 DTO
        private List<Choice> choices;
    }

    @Data
    private static class Choice { // 응답 내 선택지 DTO
        private ResponseMessage message;
    }

    @Data
    private static class ResponseMessage { // 응답 메시지 DTO
        private String content;
    }
}
