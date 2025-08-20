package com.bulmansoda.map_community.dto.marker_service;

import com.bulmansoda.map_community.model.Marker;

public class MarkerForAI {

    private double latitude;

    private double longitude;

    private String content;

    public MarkerForAI(Marker marker) {
        this.latitude = marker.getLatitude();
        this.longitude = marker.getLongitude();
        this.content = marker.getContent();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
