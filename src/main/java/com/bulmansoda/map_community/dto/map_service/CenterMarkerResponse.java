package com.bulmansoda.map_community.dto.map_service;

import com.bulmansoda.map_community.model.CenterMarker;

import java.util.List;

public class CenterMarkerResponse {

    private long id;

    private double latitude;

    private double longitude;

    private List<String> keywords;

    public CenterMarkerResponse(CenterMarker centerMarker) {
        this.id = centerMarker.getId();
        this.latitude = centerMarker.getLatitude();
        this.longitude = centerMarker.getLongitude();
        this.keywords = centerMarker.getKeywords();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
