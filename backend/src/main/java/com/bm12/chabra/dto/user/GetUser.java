package com.bm12.chabra.dto.user;

import com.bm12.chabra.model.User;
import com.bm12.chabra.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class GetUser {

    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id;
    @Schema(description = "Name of the user", example = "example")
    String name;
    @Schema(description = "Email of the user", example = "example@example.com")
    String email;

    @Schema(description = "Role of the user", example = "ADMIN")
    UserRole role;


    public GetUser(UUID id, String name, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public GetUser () {}


    /**
     * Metodo que recebe um usuário e converte para um GetUser
     *
     * @param user usuário
     * @return GetUser
     * */
    public static GetUser converter(User user) {
        return new GetUser(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }


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
}
