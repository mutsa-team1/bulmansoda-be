package com.bulmansoda.map_community.dto.cluster_center;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GptResponse {
    private String action;
    private Long id;
    private Double latitude;
    private Double longitude;
    private List<String> keywords;
}
