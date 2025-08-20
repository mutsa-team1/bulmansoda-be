package com.bulmansoda.map_community.dto.center_marker_service;

public class CommentRequest {

    private long userId;

    private long centerMarkerId;

    private String content;

    public CommentRequest(long userId, long centerMarkerId, String content) {
        this.userId = userId;
        this.centerMarkerId = centerMarkerId;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCenterMarkerId() {
        return centerMarkerId;
    }

    public void setCenterMarkerId(long centerMarkerId) {
        this.centerMarkerId = centerMarkerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
