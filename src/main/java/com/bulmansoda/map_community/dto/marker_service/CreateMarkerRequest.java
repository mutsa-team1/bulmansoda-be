package com.bulmansoda.map_community.dto.marker_service;

public class CreateMarkerRequest {

    private double latitude;

    private double longitude;

    private long userId;

    private String content;

    public CreateMarkerRequest(double latitude, double longitude, long userId, String content) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
        this.content = content;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
