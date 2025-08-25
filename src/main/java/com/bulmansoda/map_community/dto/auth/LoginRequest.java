package com.bulmansoda.map_community.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;
}
