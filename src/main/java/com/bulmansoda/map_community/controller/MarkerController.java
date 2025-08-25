package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.marker_service.CreateMarkerRequest;
import com.bulmansoda.map_community.service.MarkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marker")
public class MarkerController {

    private final MarkerService markerService;

    @Autowired
    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @PostMapping("/create")
    public long marked(@Valid @RequestBody CreateMarkerRequest request, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());

        return markerService.createMarker(request, userId);
    }

    @DeleteMapping("/delete")
    public void unmarked(@RequestParam long markerId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        markerService.deleteMarker(userId, markerId);
    }
}
