package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.center_marker_service.*;
import com.bulmansoda.map_community.service.CenterMarkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public InsideCenterMarkerResponse openCenter(@RequestParam long centerMarkerId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return centerMarkerService.openCenterMarker(userId, centerMarkerId);
    }

    @PostMapping("/like")
    public long like(@RequestParam long centerMarkerId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return centerMarkerService.likeCenterMarker(userId, centerMarkerId);
    }

    @PostMapping("/comment/create")
    public long createComment(@Valid @RequestBody CommentRequest request, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return centerMarkerService.commentCenterMarker(userId, request);
    }

    @PostMapping("/comment/like")
    public long likeComment(@RequestParam long commentId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return centerMarkerService.likeComment(userId, commentId);
    }

    @DeleteMapping("/comment/delete")
    public void deleteComment(@RequestParam long commentId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        centerMarkerService.deleteComment(userId, commentId);
    }
}
