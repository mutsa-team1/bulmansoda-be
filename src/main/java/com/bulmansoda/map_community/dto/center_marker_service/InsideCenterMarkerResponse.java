package com.bulmansoda.map_community.dto.center_marker_service;

import com.bulmansoda.map_community.model.CenterMarker;
import com.bulmansoda.map_community.model.Like;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InsideCenterMarkerResponse {

    private long likeCount;

    private boolean isLiked;

    private List<CommentResponse> comments;

    public InsideCenterMarkerResponse(long userId, CenterMarker centerMarker) {
        this.likeCount = centerMarker.getLikes().size();
        this.isLiked = false;
        for (Like like : centerMarker.getLikes()) {
            if (like.getUser().getId() == userId) {
                isLiked = true;
                break;
            }
        }
        this.comments = centerMarker.getComments().stream().map(CommentResponse::new).toList();

    }

}
