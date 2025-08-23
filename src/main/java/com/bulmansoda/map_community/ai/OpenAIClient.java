package com.bulmansoda.map_community.ai;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OpenAIClient {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final Logger log = LoggerFactory.getLogger(OpenAIClient.class);

    private final WebClient webClient;

    @Value("${openai.model}")
    private String model;

    public OpenAIClient(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl(OPENAI_API_URL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String responseJson(String system, String user, String jsonSchema) {
        try {
            ChatRequest request = createChatRequest(system, user, jsonSchema);

            ChatResponse response = webClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if (response != null && !response.getChoices().isEmpty()) {
                String content = response.getChoices().getFirst().getMessage().getContent();
                log.info("Successfully received AI response: {}", content);
                return content;
            }

            log.warn("Received an empty or invalid response from OpenAI.");
            return null;

        } catch (Exception e) {
            log.error("Error calling OpenAI API: ", e);
            return null;
        }
    }

    private ChatRequest createChatRequest(String system, String user, String jsonSchema) throws Exception {
        ChatRequest request = new ChatRequest();
        request.setModel(model);
        request.setResponse_format(new ResponseFormat("json_object"));
        request.setMessages(List.of(
                new Message("system", system),
                new Message("user", user + "\nIMPORTANT: Your output MUST strictly conform to the provided JSON schema.")
        ));
        return request;
    }

    @Data
    private static class ChatRequest {
        private String model;
        private List<Message> messages;
        private ResponseFormat response_format;
    }

    @Data
    private static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    @Data
    private static class ResponseFormat {
        private String type;

        public ResponseFormat(String type) {
            this.type = type;
        }
    }

    @Data
    private static class ChatResponse {
        private List<Choice> choices;
    }

    @Data
    private static class Choice {
        private ResponseMessage message;
    }

    @Data
    private static class ResponseMessage {
        private String content;
    }
}
