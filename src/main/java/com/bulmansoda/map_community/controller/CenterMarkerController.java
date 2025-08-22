package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.center_marker_service.CommentRequest;
import com.bulmansoda.map_community.dto.center_marker_service.InsideCenterMarkerResponse;
import com.bulmansoda.map_community.dto.center_marker_service.LikeRequest;
import com.bulmansoda.map_community.dto.center_marker_service.OpenCenterMarkerRequest;
import com.bulmansoda.map_community.service.CenterMarkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/center")
public class CenterMarkerController {

    private final CenterMarkerService centerMarkerService;

    @Autowired
    public CenterMarkerController(CenterMarkerService centerMarkerService) {
        this.centerMarkerService = centerMarkerService;
    }

    @GetMapping("/open")
    public InsideCenterMarkerResponse openCenter(@Valid @RequestBody OpenCenterMarkerRequest request) {
        return centerMarkerService.openCenterMarker(request);
    }

    @PostMapping("/like")
    public long like(@Valid @RequestBody LikeRequest request) {
        return centerMarkerService.likeCenterMarker(request);
    }

    @PostMapping("/comment/create")
    public long createComment(@Valid @RequestBody CommentRequest request) {
        return centerMarkerService.commentCenterMarker(request);
    }

    @DeleteMapping("/comment/delete")
    public void deleteComment(@RequestBody long commentId) {
        centerMarkerService.deleteComment(commentId);
    }
}
