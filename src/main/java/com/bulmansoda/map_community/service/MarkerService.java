package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.ai.GptRequest;
import com.bulmansoda.map_community.dto.ai.GptResponse;
import com.bulmansoda.map_community.dto.marker_service.CreateMarkerRequest;
import com.bulmansoda.map_community.exception.CenterMarkerNotFoundException;
import com.bulmansoda.map_community.exception.MarkerNotFoundException;
import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.CenterMarkerRepository;
import com.bulmansoda.map_community.repository.MarkerRepository;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarkerService {
    private final MarkerRepository markerRepository;
    private final UserRepository userRepository;
    private final CenterMarkerRepository centerMarkerRepository;
    private final AiClusteringService aiClusteringService; // AI 서비스 주입

    @Autowired
    public MarkerService(MarkerRepository markerRepository, UserRepository userRepository, CenterMarkerRepository centerMarkerRepository, AiClusteringService aiClusteringService) {
        this.markerRepository = markerRepository;
        this.userRepository = userRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.aiClusteringService = aiClusteringService;
    }

    public long createMarker(CreateMarkerRequest request) {
        Marker marker = new Marker();
        marker.setLatitude(request.getLatitude());
        marker.setLongitude(request.getLongitude());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new MarkerNotFoundException(request.getUserId()));
        marker.setUser(user);
        marker.setContent(request.getContent());

        GptRequest.MarkerForAI newMarkerForAI = new GptRequest.MarkerForAI();
        newMarkerForAI.setLatitude(request.getLatitude());
        newMarkerForAI.setLongitude(request.getLongitude());
        newMarkerForAI.setContent(request.getContent());

        double lat = request.getLatitude();
        double lng = request.getLongitude();
        double delta = 0.1;

        List<GptRequest.CenterMarkerForAI> existingCentersForAI = centerMarkerRepository.findCenterMarkersInArea(
                lat - delta,
                lat + delta,
                lng - delta,
                lng + delta
                )
                .stream()
                .map(center -> {
                    GptRequest.CenterMarkerForAI centerForAI = new GptRequest.CenterMarkerForAI();
                    centerForAI.setId(center.getId());
                    centerForAI.setLatitude(center.getLatitude());
                    centerForAI.setLongitude(center.getLongitude());
                    centerForAI.setKeywords(center.getKeywords());
                    return centerForAI;
                }).collect(Collectors.toList());

        GptResponse aiResponse = aiClusteringService.clusterOrIntegrate(newMarkerForAI, existingCentersForAI);

        CenterMarker targetCenter;
        if ("UPDATE".equals(aiResponse.getAction())) {
            targetCenter = centerMarkerRepository.findById(aiResponse.getId())
                    .orElseThrow(() -> new CenterMarkerNotFoundException(aiResponse.getId()));
        } else {
            targetCenter = new CenterMarker();
        }

        targetCenter.setLatitude(aiResponse.getLatitude());
        targetCenter.setLongitude(aiResponse.getLongitude());
        targetCenter.setKeywords(aiResponse.getKeywords());
        centerMarkerRepository.save(targetCenter);

        marker.setCenterMarker(targetCenter);
        markerRepository.save(marker);

        return marker.getId();
    }

    public void deleteMarker(long markerId) {
        markerRepository.deleteById(markerId);
    }
}
