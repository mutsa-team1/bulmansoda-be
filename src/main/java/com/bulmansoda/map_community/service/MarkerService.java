package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.cluster_center.GptRequest;
import com.bulmansoda.map_community.dto.cluster_center.GptResponse;
import com.bulmansoda.map_community.dto.marker_service.CreateMarkerRequest;
import com.bulmansoda.map_community.exception.CenterMarkerNotFoundException;
import com.bulmansoda.map_community.exception.MarkerNotFoundException;
import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Marker;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.CenterMarkerRepository;
import com.bulmansoda.map_community.repository.MarkerRepository;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class MarkerService {
    private final MarkerRepository markerRepository;
    private final UserRepository userRepository;
    private final CenterMarkerRepository centerMarkerRepository;
    private final AiClusteringService aiClusteringService; // AI 서비스 주입

    public MarkerService(MarkerRepository markerRepository, UserRepository userRepository, CenterMarkerRepository centerMarkerRepository, AiClusteringService aiClusteringService) {
        this.markerRepository = markerRepository;
        this.userRepository = userRepository;
        this.centerMarkerRepository = centerMarkerRepository;
        this.aiClusteringService = aiClusteringService;
    }

    public long createMarker(CreateMarkerRequest request) {
        // 1. 새로운 마커 객체 생성
        Marker marker = new Marker();
        marker.setLatitude(request.getLatitude());
        marker.setLongitude(request.getLongitude());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new MarkerNotFoundException(request.getUserId()));
        marker.setUser(user);
        marker.setContent(request.getContent());

        // 2. AI 클러스터링 요청을 위한 데이터 준비
        GptRequest.MarkerForAI newMarkerForAI = new GptRequest.MarkerForAI();
        newMarkerForAI.setLatitude(request.getLatitude());
        newMarkerForAI.setLongitude(request.getLongitude());
        newMarkerForAI.setContent(request.getContent());

        List<GptRequest.CenterMarkerForAI> existingCentersForAI = centerMarkerRepository.findAll().stream().map(center -> {
            GptRequest.CenterMarkerForAI centerForAI = new GptRequest.CenterMarkerForAI();
            centerForAI.setId(center.getId());
            centerForAI.setLatitude(center.getLatitude());
            centerForAI.setLongitude(center.getLongitude());
            centerForAI.setKeywords(center.getKeywords());
            return centerForAI;
        }).collect(Collectors.toList());

        // 3. AI 서비스 호출
        GptResponse aiResponse = aiClusteringService.clusterOrIntegrate(newMarkerForAI, existingCentersForAI);

        // 4. AI 응답에 따른 처리
        CenterMarker targetCenter;
        if ("UPDATE".equals(aiResponse.getAction())) {
            // 기존 클러스터 업데이트
            targetCenter = centerMarkerRepository.findById(aiResponse.getId())
                    .orElseThrow(() -> new CenterMarkerNotFoundException(aiResponse.getId()));
        } else { // "CREATE"
            // 새 클러스터 생성
            targetCenter = new CenterMarker();
        }

        // 위도, 경도, 키워드 업데이트 또는 설정
        targetCenter.setLatitude(aiResponse.getLatitude());
        targetCenter.setLongitude(aiResponse.getLongitude());
        targetCenter.setKeywords(aiResponse.getKeywords());
        centerMarkerRepository.save(targetCenter);

        // 새 마커를 대상 클러스터에 연결하고 저장
        marker.setCenterMarker(targetCenter);
        markerRepository.save(marker);

        return marker.getId();
    }

    public void deleteMarker(long markerId) {
        markerRepository.deleteById(markerId);
    }
}
