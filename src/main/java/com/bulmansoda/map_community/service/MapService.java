package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.map_service.CenterMarkerResponse;
import com.bulmansoda.map_community.dto.map_service.MarkerResponse;
import com.bulmansoda.map_community.exception.CenterMarkerNotFoundException;
import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import com.bulmansoda.map_community.repository.CenterMarkerRepository;
import com.bulmansoda.map_community.repository.MarkerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class MapService {

    private static final Logger log = LogManager.getLogger(MapService.class);
    private final MarkerRepository markerRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    @Autowired
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

    @Scheduled(fixedRate = 3600000)
    public void cleanUpOldCenterMarkers() {
        log.info("Running scheduled job to clean up old center markers...");

        Timestamp fiveHoursAgo = Timestamp.from(Instant.now().minus(5, ChronoUnit.HOURS));
        List<CenterMarker> oldCenterMarkers = centerMarkerRepository.findByUpdatedAtBefore(fiveHoursAgo);

        if(oldCenterMarkers.isEmpty()) {
            log.info("No old center markers to clean up.");
            return;
        }

        log.info("Found {} old center markers to delete. Deleting now...", oldCenterMarkers);
        centerMarkerRepository.deleteAll(oldCenterMarkers);
        log.info("Successfully deleted old center markers.");
    }
}
