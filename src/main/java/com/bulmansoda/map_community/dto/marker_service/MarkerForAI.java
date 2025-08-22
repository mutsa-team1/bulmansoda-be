package com.bulmansoda.map_community.dto.marker_service;

import com.bulmansoda.map_community.model.Marker;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarkerForAI {

    private double latitude;

    private double longitude;

    private String content;

    public MarkerForAI(Marker marker) {
        this.latitude = marker.getLatitude();
        this.longitude = marker.getLongitude();
        this.content = marker.getContent();
    }

}
