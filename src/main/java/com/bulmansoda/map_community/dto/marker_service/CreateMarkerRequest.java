package com.bulmansoda.map_community.dto.marker_service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Data // Getter, Setter, toString 등을 자동으로 생성합니다.
@NoArgsConstructor // Jackson 라이브러리가 JSON을 객체로 변환할 때 필요한 기본 생성자를 추가합니다.
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 추가합니다.
public class CreateMarkerRequest {

    @NotNull(message = "Latitude is required")
    private double latitude;

    @NotNull(message = "Longitude is required")
    private double longitude;

    @NotNull(message = "User id is required")
    private long userId;

    @NotBlank(message = "Content is required")
    private String content;


}
