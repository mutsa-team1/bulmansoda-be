package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.ai.OpenAIClient;
import com.bulmansoda.map_community.ai.PromptBuilder;
import com.bulmansoda.map_community.dto.ai.GptRequest;
import com.bulmansoda.map_community.dto.ai.GptResponse;
import com.bulmansoda.map_community.exception.AiProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AiClusteringService {

    private final OpenAIClient openAIClient;
    private final PromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;

    @Autowired
    public AiClusteringService(OpenAIClient openAIClient, PromptBuilder promptBuilder, ObjectMapper objectMapper) {
        this.openAIClient = openAIClient;
        this.promptBuilder = promptBuilder;
        this.objectMapper = objectMapper;
    }


    public GptResponse clusterOrIntegrate(GptRequest.MarkerForAI marker, List<GptRequest.CenterMarkerForAI> centers) {

        List<GptRequest.CenterMarkerForAI> nonNullCenters = (centers == null) ? Collections.emptyList() : centers;

        String systemPrompt = promptBuilder.system();
        String userPrompt = promptBuilder.user(marker, nonNullCenters);
        String jsonSchema = promptBuilder.jsonSchema();

        String jsonResponse = openAIClient.responseJson(systemPrompt, userPrompt, jsonSchema);

        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            throw new AiProcessingException("Received no response from AI service.");
        }

        try {
            return objectMapper.readValue(jsonResponse, GptResponse.class);
        } catch (Exception e) {
            throw new AiProcessingException("Failed to parse AI response JSON", e);
        }
    }
}
