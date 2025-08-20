package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.map_service.CenterMarkerResponse;
import com.bulmansoda.map_community.dto.map_service.MarkerResponse;
import com.bulmansoda.map_community.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/markers")
    public List<MarkerResponse> showMarkers(
            @RequestParam("minLat") double minLat,
            @RequestParam("maxLat") double maxLat,
            @RequestParam("minLng") double minLng,
            @RequestParam("maxLng") double maxLng) {
        return mapService.showMarkers(minLat, maxLat, minLng, maxLng);
    }

    @GetMapping("/centerMarkers")
    public List<CenterMarkerResponse> showCenterMarkers(
            @RequestParam("minLat") double minLat,
            @RequestParam("maxLat") double maxLat,
            @RequestParam("minLng") double minLng,
            @RequestParam("maxLng") double maxLng) {
        return mapService.showCenterMarkers(minLat, maxLat, minLng, maxLng);
    }

    @DeleteMapping("/refresh")
    public void refreshAll() {
        mapService.refreshMap();
    }

    @DeleteMapping("/fixed")
    public void fixed(@RequestBody long centerMarkerId) {
        mapService.problemSolved(centerMarkerId);
    }
}
