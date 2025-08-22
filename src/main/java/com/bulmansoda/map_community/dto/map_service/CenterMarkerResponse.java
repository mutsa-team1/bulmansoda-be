package com.bulmansoda.map_community.dto.map_service;

import com.bulmansoda.map_community.model.CenterMarker;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
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

}
