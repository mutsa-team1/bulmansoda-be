package com.bulmansoda.map_community.dto.center_marker_service;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OpenCenterMarkerRequest {

    @NotNull(message = "User id is required")
    private long userId;

    @NotNull(message = "Center Marker id is required")
    private long centerMarkerId;

}
