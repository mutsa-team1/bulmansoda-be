package com.bulmansoda.map_community.dto.center_marker_service;

import com.bulmansoda.map_community.model.Comment;

import java.sql.Timestamp;

public class CommentResponse {

    private String name;

    private String content;

    private Timestamp createdAt;

    public CommentResponse(Comment comment) {
        this.name = comment.getUser().getName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
