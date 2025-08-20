package com.bulmansoda.map_community.dto.map_service;

import com.bulmansoda.map_community.model.Marker;

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

    public long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(long markerId) {
        this.markerId = markerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
