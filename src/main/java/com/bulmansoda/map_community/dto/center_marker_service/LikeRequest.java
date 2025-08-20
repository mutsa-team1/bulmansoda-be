package com.bulmansoda.map_community.dto.center_marker_service;

public class LikeRequest {

    private long userId;

    private long centerMarkerId;

    public LikeRequest(long userId, long centerMarkerId) {
        this.userId = userId;
        this.centerMarkerId = centerMarkerId;
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
}
