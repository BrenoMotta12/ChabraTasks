package com.bm12.chabra.dto.space;

import com.bm12.chabra.model.ListTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class UpdateSpace {

    @NotNull(message = "Id cannot be null")
    @Schema(description = "ID of the space", example = "example")
    private UUID id;
    @Schema(description = "Name of the space", example = "example")
    private String name;

    @Schema(description = "Description of the space", example = "example")
    private String description;

    @Schema(description = "Color of the space", example = "example")
    private String color;

    public UpdateSpace(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public UpdateSpace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

