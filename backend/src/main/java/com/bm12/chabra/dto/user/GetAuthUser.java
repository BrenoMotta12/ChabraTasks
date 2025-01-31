package com.bm12.chabra.dto.user;

import com.bm12.chabra.model.User;
import com.bm12.chabra.model.enums.UserRole;

import java.util.UUID;

public class GetAuthUser {

    private UUID id;
    private String email;
    private String name;
    private UserRole role;
    private String token;

    public GetAuthUser(UUID id, String email, String name, UserRole role, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.token = token;
    }

    public GetAuthUser(User user, String token) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
        this.token = token;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
