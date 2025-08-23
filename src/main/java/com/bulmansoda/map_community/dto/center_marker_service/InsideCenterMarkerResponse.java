package com.bulmansoda.map_community.dto.center_marker_service;

import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.CenterMarkerLike;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InsideCenterMarkerResponse {

    private int likeCount;

    private boolean isLiked;

    private List<CommentResponse> comments;

    public InsideCenterMarkerResponse(long userId, CenterMarker centerMarker) {
        this.likeCount = centerMarker.getCenterMarkerLikes().size();
        this.isLiked = false;
        for (CenterMarkerLike centerMarkerLike : centerMarker.getCenterMarkerLikes()) {
            if (centerMarkerLike.getUser().getId() == userId) {
                isLiked = true;
                break;
            }
        }
        this.comments = centerMarker.getComments().stream()
                .map(comment -> new CommentResponse(userId, comment)).toList();

    }

}
