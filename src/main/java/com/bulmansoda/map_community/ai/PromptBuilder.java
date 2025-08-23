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
        return "You are an intelligent geospatial clustering AI. Your task is to analyze a new individual marker and a list of existing cluster markers. " +
                "You must decide whether the new marker belongs to one of the existing clusters or if it should form a new cluster. " +
                "Based on your decision, you will either provide updated information for an existing cluster or data for a new one. " +
                "Always follow the specified keyword hierarchy: Keyword 1 is the main theme, Keywords 2 and 3 are specific details.";
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
                "1.  **Analyze:** Determine if the new marker is thematically and geographically related to any existing cluster.\n" +
                "2.  **Decide & Respond with JSON:**\n" +
                "    -   **If it belongs to an existing cluster (DECISION: UPDATE):**\n" +
                "        -   Set `action` to \"UPDATE\".\n" +
                "        -   Set `id` to the ID of the matched cluster.\n" +
                "        -   **Recalculate** the cluster's `latitude` and `longitude` to include the new marker.\n" +
                "        -   **Regenerate** the cluster's `keywords` to best represent all its markers, including the new one.\n" +
                "    -   **If it does NOT belong to any existing cluster (DECISION: CREATE):**\n" +
                "        -   Set `action` to \"CREATE\".\n" +
                "        -   Use the new marker's coordinates for the `latitude` and `longitude`.\n" +
                "        -   **Extract** three hierarchical keywords into a simple JSON array of strings.\n" +
                "        -   The `id` field should be omitted.\n\n" +
                "Please provide your response in a single, clean JSON object according to the schema.";
    }

    public String jsonSchema() {
        return """
                 {
                   "type": "object",
                   "properties": {
                     "action": {
                       "type": "string",
                       "description": "The decision made by the AI. Either 'UPDATE' or 'CREATE'.",
                       "enum": ["UPDATE", "CREATE"]
                     },
                     "id": {
                       "type": "integer",
                       "description": "The ID of the cluster to update. Only present if action is 'UPDATE'."
                     },
                     "latitude": {
                       "type": "number",
                       "description": "The recalculated or new latitude of the cluster."
                     },
                     "longitude": {
                       "type": "number",
                       "description": "The recalculated or new longitude of the cluster."
                     },
                     "keywords": {
                       "type": "array",
                       "description": "An array of exactly three keywords (Theme, Detail, Detail).",
                       "items": { "type": "string" },
                       "minItems": 3,
                       "maxItems": 3
                     }
                   },
                   "required": ["action", "latitude", "longitude", "keywords"]
                 }
                 """;
    }
}
