package com.bulmansoda.map_community.ai;

import com.bulmansoda.map_community.dto.ai.GptRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String system() {
        return "You are an intelligent geospatial clustering AI for a real-time traffic map. Your primary language is Korean, but you are fluent in English. " +
                "You will analyze a new traffic marker and a list of existing clusters. " +
                "Your task is to decide if the new marker is relevant to a traffic situation, and if so, whether it belongs to an existing cluster or should form a new one. " +
                "Your response MUST be in the same language as the 'content' of the new marker.";
    }


    public String user(GptRequest.MarkerForAI newMarker, List<GptRequest.CenterMarkerForAI> existingCenters) {
        String newMarkerJson;
        String existingCentersJson;
        try {
            newMarkerJson = objectMapper.writeValueAsString(newMarker);
            existingCentersJson = objectMapper.writeValueAsString(existingCenters);
        } catch (JsonProcessingException e) {
            return "Error formatting data.";
        }

        return "Here is a new marker and a list of existing cluster markers.\n\n" +
                "**New Individual Marker:**\n" + newMarkerJson + "\n\n" +
                "**List of Existing Cluster Markers:**\n" + existingCentersJson + "\n\n" +
                "**Your Task:**\n" +
                "1.  **Analyze Relevance:** First, determine if the 'content' of the new marker describes a plausible traffic situation (e.g., accident, congestion, protest, construction). Content like '배고프다', 'I am hungry', 'test', or random characters is NOT relevant.\n" +
                "2.  **Decide & Respond with JSON:**\n" +
                "    -   **If the content is NOT relevant (DECISION: ERROR):**\n" +
                "        -   Set `action` to \"ERROR\".\n" +
                "        -   Omit all other fields.\n" +
                "    -   **If the content IS relevant and belongs to an existing cluster (DECISION: UPDATE):**\n" +
                "        -   Set `action` to \"UPDATE\".\n" +
                "        -   Set `id` to the ID of the matched cluster.\n" +
                "        -   Recalculate the cluster's `latitude` and `longitude`.\n" +
                "        -   Regenerate the cluster's `keywords` in the SAME LANGUAGE as the new marker's content.\n" +
                "    -   **If the content IS relevant and forms a new cluster (DECISION: CREATE):**\n" +
                "        -   Set `action` to \"CREATE\".\n" +
                "        -   Use the new marker's coordinates for `latitude` and `longitude`.\n" +
                "        -   Extract three hierarchical keywords from the new marker's content, in the SAME LANGUAGE as the content.\n\n" +
                "Please provide your response in a single, clean JSON object according to the schema.";
    }


    public String jsonSchema() {
        return """
                {
                  "type": "object",
                  "properties": {
                    "action": {
                      "type": "string",
                      "description": "The decision made by the AI. Can be 'UPDATE', 'CREATE', or 'ERROR'.",
                      "enum": ["UPDATE", "CREATE", "ERROR"]
                    },
                    "id": {
                      "type": "integer",
                      "description": "The ID of the cluster to update. Only present if action is 'UPDATE'."
                    },
                    "latitude": {
                      "type": "number",
                      "description": "The recalculated or new latitude of the cluster. Not present if action is 'ERROR'."
                    },
                    "longitude": {
                      "type": "number",
                      "description": "The recalculated or new longitude of the cluster. Not present if action is 'ERROR'."
                    },
                    "keywords": {
                      "type": "array",
                      "description": "An array of exactly three keywords. Not present if action is 'ERROR'.",
                      "items": { "type": "string" },
                      "minItems": 3,
                      "maxItems": 3
                    }
                  },
                  "required": ["action"]
                }
                """;
    }
}
