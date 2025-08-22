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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
public class MarkerService {
    private final MarkerRepository markerRepository;

    private final UserRepository userRepository;

    private final CenterMarkerRepository centerMarkerRepository;

    private static final double CLUSTERING_RADIUS_KM = 0.5;

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

        CenterMarker center = findOrCreateClusterForMarker(marker); // This now contains our temporary clustering logic
        marker.setCenterMarker(center);

        updateCenterKeywords(center, marker.getContent()); // Add the new marker's content to the cluster's keywords (simple version)

        markerRepository.save(marker);
        centerMarkerRepository.save(center);

        return marker.getId();
    }

    /**
     * This is the temporary clustering logic.
     * It finds the nearest CenterMarker within a defined radius.
     * If none are found, it creates a new one.
     */
    private CenterMarker findOrCreateClusterForMarker(Marker newMarker) {
        List<CenterMarker> allCenters = centerMarkerRepository.findAll();

        CenterMarker closestCenter = null;
        double minDistance = Double.MAX_VALUE;

        // Find the nearest existing center
        for (CenterMarker center : allCenters) {
            double distance = calculateDistance(
                    newMarker.getLatitude(), newMarker.getLongitude(),
                    center.getLatitude(), center.getLongitude()
            );
            if (distance < minDistance) {
                minDistance = distance;
                closestCenter = center;
            }
        }

        // If the closest center is within our radius, use it.
        if (closestCenter != null && minDistance <= CLUSTERING_RADIUS_KM) {
            // Optional: Recalculate the center point of the cluster to be more accurate
            // For now, we'll just return the existing center.
            return closestCenter;
        } else {
            // Otherwise, no center is close enough, so create a new one.
            CenterMarker newCenter = new CenterMarker();
            newCenter.setLatitude(newMarker.getLatitude());
            newCenter.setLongitude(newMarker.getLongitude());
            newCenter.setMarkers(new ArrayList<>()); // Initialize lists
            newCenter.setKeywords(new ArrayList<>());
            return centerMarkerRepository.save(newCenter);
        }
    }

    /**
     * A simple placeholder for updating the keywords of a cluster.
     * This version just takes the first 3 words of the new marker's content.
     */
    private void updateCenterKeywords(CenterMarker center, String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            return;
        }
        // A simple logic: split content into words and take the first few
        List<String> words = Arrays.asList(newContent.toLowerCase().split("\\s+"));
        List<String> currentKeywords = center.getKeywords();
        if (currentKeywords == null) {
            currentKeywords = new ArrayList<>();
        }

        for (int i = 0; i < Math.min(words.size(), 3); i++) {
            String word = words.get(i);
            if (!currentKeywords.contains(word)) {
                currentKeywords.add(word);
            }
        }
        // Ensure we don't have too many keywords, trim if necessary
        if (currentKeywords.size() > 10) {
            center.setKeywords(new ArrayList<>(currentKeywords.subList(0, 10)));
        } else {
            center.setKeywords(currentKeywords);
        }
    }


    /**
     * Calculates the distance between two points on Earth in kilometers using the Haversine formula.
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadiusKm = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }

    public void deleteMarker(long markerId) {
        markerRepository.deleteById(markerId);
    }

}
