package com.bm12.chabra.dto.user;

import com.bm12.chabra.model.User;
import com.bm12.chabra.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class SaveUser {

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

    @Schema(description = "Role of the user, defines which features the user can access", example = "Admin")
    private UserRole role;

    public SaveUser() {}

    public SaveUser(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
