package com.bulmansoda.map_community.dto.user_service;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {

    @NotBlank(message = "User name is required")
    private String name;

    @NotBlank(message = "User phone number is required")
    private String phoneNumber;

    public CreateUserRequest(String name) {
        this.name = name;
    }

}
