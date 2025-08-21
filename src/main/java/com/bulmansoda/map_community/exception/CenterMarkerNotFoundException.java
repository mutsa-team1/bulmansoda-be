package com.bulmansoda.map_community.exception;

public class CenterMarkerNotFoundException extends RuntimeException {
    public CenterMarkerNotFoundException(Long id) {
        super("CenterMarker Not Found with id " + id);
    }
}
