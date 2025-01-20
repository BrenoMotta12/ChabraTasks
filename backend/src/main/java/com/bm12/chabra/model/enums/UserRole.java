package com.bm12.chabra.model.enums;

public enum UserRole {

    ADMIN("admin"),
    MODERATOR("moderator"),
    USER("user");

    private String role;

    UserRole (String role) {
        this.role = role;
    }

    public String getUserRole() {
        return role;
    }
}
