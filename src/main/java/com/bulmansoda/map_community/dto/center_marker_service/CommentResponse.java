package com.bulmansoda.map_community.dto.center_marker_service;

import com.bulmansoda.map_community.model.Comment;

import java.sql.Timestamp;

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

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
