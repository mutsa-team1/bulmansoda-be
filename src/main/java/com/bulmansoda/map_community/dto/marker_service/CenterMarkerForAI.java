package com.bulmansoda.map_community.dto.marker_service;

import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class CenterMarkerForAI {

    private long id;

    private double latitude;

    private double longitude;

    private List<Map<String, Map<Double, Double>>> markers;

    private List<String> keywords;

    public CenterMarkerForAI(CenterMarker center) {
        this.id = center.getId();
        this.latitude = center.getLatitude();
        this.longitude = center.getLongitude();
        List<Marker> markerList = center.getMarkers();
        this.markers = markerList.stream()
                .map(marker -> {
                    Map<Double, Double> latLng = Map.of(marker.getLatitude(), marker.getLongitude());
                    return Map.of(marker.getContent(), latLng);
                }).toList();
        this.keywords = center.getKeywords();
    }

}
