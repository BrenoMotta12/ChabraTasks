package com.bm12.chabra.dto.user;

import com.bm12.chabra.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
    @Schema(description = "Email address of the user", example = "example@example.com.br")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Length(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Schema(description = "Password of the user, must be between 8 and 50 characters", example = "P@ssw0rd123")
    private String password;

    @Length(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Schema(description = "Password of the user, must be between 8 and 50 characters", example = "P@ssw0rd123")
    private String newPassword;

    @Schema(description = "Role of the user, defines which features the user can access", example = "Admin")
    private UserRole role;



    public UpdateUser(UUID id, String name, String email, String password, String newPassword, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
        this.role = role;
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

    public String getNewPassword() {
        return newPassword;
    }

    public UserRole getRole() {
        return role;
    }

}
