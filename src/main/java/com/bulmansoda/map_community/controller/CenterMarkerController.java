package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.center_marker_service.CommentRequest;
import com.bulmansoda.map_community.dto.center_marker_service.InsideCenterMarkerResponse;
import com.bulmansoda.map_community.dto.center_marker_service.LikeRequest;
import com.bulmansoda.map_community.dto.center_marker_service.OpenCenterMarkerRequest;
import com.bulmansoda.map_community.service.CenterMarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/center")
public class CenterMarkerController {

    private final CenterMarkerService centerMarkerService;

    @Autowired
    public CenterMarkerController(CenterMarkerService centerMarkerService) {
        this.centerMarkerService = centerMarkerService;
    }

    @GetMapping("/open")
    public InsideCenterMarkerResponse openCenter(@RequestBody OpenCenterMarkerRequest request) {
        return centerMarkerService.openCenterMarker(request);
    }

    @PostMapping("/like")
    public long like(@RequestBody LikeRequest request) {
        return centerMarkerService.likeCenterMarker(request);
    }

    @PostMapping("/comment")
    public long comment(@RequestBody CommentRequest request) {
        return centerMarkerService.commentCenterMarker(request);
    }
}
