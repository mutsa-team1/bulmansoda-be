package com.bulmansoda.map_community.dto.user_service;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeNameRequest {

    @NotNull(message = "User id is required")
    private long userId;

    @NotNull(message = "New name is required")
    private String name;

}
