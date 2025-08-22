package com.bulmansoda.map_community.dto.ai;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON 변환 시 제외
public class GptResponse {
    private String action; // "UPDATE" or "CREATE"
    private Long id; // action이 "UPDATE"일 때, 해당하는 통합마커의 PK
    private Double latitude;
    private Double longitude;
    private List<String> keywords; // 3개의 키워드
}
