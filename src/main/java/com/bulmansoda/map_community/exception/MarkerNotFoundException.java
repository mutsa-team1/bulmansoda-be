package com.bulmansoda.map_community.exception;

public class MarkerNotFoundException extends RuntimeException {
    public MarkerNotFoundException(Long id) {
        super("Marker not found with id " + id);
    }
}
