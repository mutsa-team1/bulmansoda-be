package com.bulmansoda.map_community.dto.center_marker_service;

public class OpenCenterMarkerRequest {

    private long userId;

    private long centerMarkerId;

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
