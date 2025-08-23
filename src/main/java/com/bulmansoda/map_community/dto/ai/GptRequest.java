package com.bulmansoda.map_community.dto.ai;

import lombok.Data;
import java.util.List;

public class GptRequest {

    @Data
    public static class MarkerForAI {
        private double latitude;
        private double longitude;
        private String content;
    }

    @Data
    public static class CenterMarkerForAI {
        private Long id;
        private double latitude;
        private double longitude;
        private List<String> keywords;

    }

}
