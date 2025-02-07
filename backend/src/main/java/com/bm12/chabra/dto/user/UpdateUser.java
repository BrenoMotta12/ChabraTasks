package com.bm12.chabra.dto.user;

import com.bm12.chabra.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateUser {

    @NotNull(message = "Id cannot be null")
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id;

    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "Name of the user", example = "example")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    private String password;

    @Schema(description = "Role of the user, defines which features the user can access", example = "Admin")
    private UserRole role;


    private Boolean deleted;


    public UpdateUser(UUID id, String name, String email, String password, UserRole role, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.deleted = deleted;
    }

    public UpdateUser() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
