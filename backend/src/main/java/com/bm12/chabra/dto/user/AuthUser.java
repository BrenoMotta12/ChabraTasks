package com.bm12.chabra.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class AuthUser {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Schema(description = "Email address of the user", example = "example@example.com.br")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Length(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Schema(description = "Password of the user, must be between 8 and 50 characters", example = "P@ssw0rd123")
    private String password;

    public AuthUser() {}

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
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
}
