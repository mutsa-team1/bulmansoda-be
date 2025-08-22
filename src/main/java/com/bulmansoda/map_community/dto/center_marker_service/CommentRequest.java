package com.bulmansoda.map_community.dto.center_marker_service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest {

    @NotNull(message = "User id is required")
    private long userId;

    @NotNull(message = "Center marker id is required")
    private long centerMarkerId;

    @NotBlank(message = "Content is required")
    private String content;

    public CommentRequest(long userId, long centerMarkerId, String content) {
        this.userId = userId;
        this.centerMarkerId = centerMarkerId;
        this.content = content;
    }

}
