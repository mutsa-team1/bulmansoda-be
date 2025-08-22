package com.bulmansoda.map_community.dto.marker_service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateMarkerRequest {

    @NotNull(message = "Latitude is required")
    private double latitude;

    @NotNull(message = "Longitude is required")
    private double longitude;

    @NotNull(message = "User id is required")
    private long userId;

    @NotBlank(message = "Content is required")
    private String content;

    public CreateMarkerRequest(double latitude, double longitude, long userId, String content) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
        this.content = content;
    }

}
