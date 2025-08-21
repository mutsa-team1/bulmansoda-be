package com.bulmansoda.map_community.dto.map_service;

import com.bulmansoda.map_community.model.CenterMarker;

import java.util.List;

public class CenterMarkerResponse {

    private long centerMarkerId;

    private double latitude;

    private double longitude;

    private List<String> keywords;

    public CenterMarkerResponse(CenterMarker centerMarker) {
        this.centerMarkerId = centerMarker.getId();
        this.latitude = centerMarker.getLatitude();
        this.longitude = centerMarker.getLongitude();
        this.keywords = centerMarker.getKeywords();
    }

    public long getCenterMarkerId() {
        return centerMarkerId;
    }

    public void setCenterMarkerId(long centerMarkerId) {
        this.centerMarkerId = centerMarkerId;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
