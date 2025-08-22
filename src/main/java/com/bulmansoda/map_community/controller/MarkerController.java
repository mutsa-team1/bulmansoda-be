package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.marker_service.CreateMarkerRequest;
import com.bulmansoda.map_community.service.MarkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public long marked(@Valid @RequestBody CreateMarkerRequest request) {
        return markerService.createMarker(request);
    }

    @DeleteMapping("/delete")
    public void unmarked(@RequestBody long markerId) {
        markerService.deleteMarker(markerId);
    }
}
