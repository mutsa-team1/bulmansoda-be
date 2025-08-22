package com.bulmansoda.map_community.dto.center_marker_service;

import com.bulmansoda.map_community.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class CommentResponse {

    private long commentId;

    private String name;

    private long userId;

    private String content;

    private Timestamp createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.name = comment.getUser().getName();
        this.userId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

}
