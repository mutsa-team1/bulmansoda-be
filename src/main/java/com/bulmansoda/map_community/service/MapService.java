package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.map_service.CenterMarkerResponse;
import com.bulmansoda.map_community.dto.map_service.MarkerResponse;
import com.bulmansoda.map_community.exception.CenterMarkerNotFoundException;
import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import com.bulmansoda.map_community.repository.CenterMarkerRepository;
import com.bulmansoda.map_community.repository.MarkerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class MapService {

    private final MarkerRepository markerRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    public MapService(MarkerRepository markerRepository, CenterMarkerRepository centerMarkerRepository) {
        this.markerRepository = markerRepository;
        this.centerMarkerRepository = centerMarkerRepository;
    }

    public List<MarkerResponse> showMarkers(double minLat, double maxLat, double minLng, double maxLng) {
        List<Marker> markers = markerRepository.findMarkersInArea(minLat, maxLat, minLng, maxLng);
        return markers.stream().map(MarkerResponse::new).toList();
    }

    public List<CenterMarkerResponse> showCenterMarkers(double minLat, double maxLat, double minLng, double maxLng) {
        List<CenterMarker> centers = centerMarkerRepository.findCenterMarkersInArea(minLat, maxLat, minLng, maxLng);
        return centers.stream().map(CenterMarkerResponse::new).toList();
    }

    public void problemSolved(long centerId) {
        CenterMarker center = centerMarkerRepository.findById(centerId)
                .orElseThrow(() -> new CenterMarkerNotFoundException(centerId));
        markerRepository.deleteAllByCenterMarker(center);
        centerMarkerRepository.delete(center);
    }

    public void refreshMap() {
        markerRepository.deleteAll();
        centerMarkerRepository.deleteAll();
    }
}
