package com.bulmansoda.map_community.dto.user_service;

public class CreateUserRequest {

    private String name;

    public CreateUserRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
