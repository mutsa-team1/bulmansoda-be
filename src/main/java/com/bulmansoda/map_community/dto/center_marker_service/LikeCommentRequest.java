package com.bulmansoda.map_community.dto.center_marker_service;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeCommentRequest {
    @NotNull(message = "User id is required")
    private long userId;

    @NotNull(message = "Comment id is required")
    private long commentId;

    public LikeCommentRequest(long userId, long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
