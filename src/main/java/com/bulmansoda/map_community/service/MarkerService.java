package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.marker_service.CenterMarkerForAI;
import com.bulmansoda.map_community.dto.marker_service.CreateMarkerRequest;
import com.bulmansoda.map_community.dto.marker_service.MarkerForAI;
import com.bulmansoda.map_community.exception.MarkerNotFoundException;
import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.CenterMarkerRepository;
import com.bulmansoda.map_community.repository.MarkerRepository;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class MarkerService {
    private final MarkerRepository markerRepository;

    private final UserRepository userRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    public MarkerService(MarkerRepository markerRepository, UserRepository userRepository, CenterMarkerRepository centerMarkerRepository) {
        this.markerRepository = markerRepository;
        this.userRepository = userRepository;
        this.centerMarkerRepository = centerMarkerRepository;
    }

    public long createMarker(CreateMarkerRequest request) {
        Marker marker = new Marker();
        marker.setLatitude(request.getLatitude());
        marker.setLongitude(request.getLongitude());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new MarkerNotFoundException(request.getUserId()));
        marker.setUser(user);
        marker.setContent(request.getContent());

        CenterMarker center = clusterMarker(marker);
        marker.setCenterMarker(center);
        markerRepository.save(marker);
        centerMarkerRepository.save(center);

        return  marker.getId();
    }

    private CenterMarker clusterMarker(Marker marker) {
        List<CenterMarker> centers = centerMarkerRepository.findAll();
        List<CenterMarkerForAI> processedCenters = centers.stream().map(CenterMarkerForAI::new).toList();
        MarkerForAI processedMarker = new MarkerForAI(marker);
        //CenterMarker center = gptHandling(processedMarker, processedCenters);
        CenterMarker center = new CenterMarker();
        center.setLatitude(marker.getLatitude());
        center.setLongitude(marker.getLongitude());

        return center;
    }

    public void deleteMarker(long markerId) {
        markerRepository.deleteById(markerId);
    }

}
