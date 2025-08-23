package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.center_marker_service.*;
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
    public InsideCenterMarkerResponse openCenter(@RequestParam long userId, @RequestParam long centerMarkerId) {
        return centerMarkerService.openCenterMarker(userId, centerMarkerId);
    }

    @PostMapping("/like")
    public long like(@Valid @RequestBody LikeCenterMarkerRequest request) {
        return centerMarkerService.likeCenterMarker(request);
    }

    @PostMapping("/comment/create")
    public long createComment(@Valid @RequestBody CommentRequest request) {
        return centerMarkerService.commentCenterMarker(request);
    }

    @PostMapping("/comment/like")
    public long likeComment(@Valid @RequestBody LikeCommentRequest request) {
        return centerMarkerService.likeComment(request);
    }

    @DeleteMapping("/comment/delete")
    public void deleteComment(@RequestBody long commentId) {
        centerMarkerService.deleteComment(commentId);
    }
}
