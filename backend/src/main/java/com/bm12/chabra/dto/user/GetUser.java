package com.bm12.chabra.dto.user;

import com.bm12.chabra.model.User;
import com.bm12.chabra.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetUser {

    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    @Schema(description = "Name of the user", example = "example")
    private String name;
    @Schema(description = "Email of the user", example = "example@example.com")
    private String email;

    @Schema(description = "Role of the user", example = "ADMIN")
    private UserRole role;

    private LocalDateTime deletedAt;


    public GetUser(UUID id, String name, String email, UserRole role, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.deletedAt = deletedAt;
    }

    public GetUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.deletedAt = user.getDeletedAt() == null ? null : user.getDeletedAt();
    }

    public GetUser () {}



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
