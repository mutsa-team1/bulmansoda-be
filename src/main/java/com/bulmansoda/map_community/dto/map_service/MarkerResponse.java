package com.bulmansoda.map_community.dto.map_service;

import com.bulmansoda.map_community.model.Marker;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarkerResponse {

    private long markerId;

    private long userId;

    private double latitude;

    private double longitude;

    private String content;

    public MarkerResponse(Marker marker) {
        this.markerId = marker.getId();
        this.userId = marker.getUser().getId();
        this.latitude = marker.getLatitude();
        this.longitude = marker.getLongitude();
        this.content = marker.getContent();
    }

}
