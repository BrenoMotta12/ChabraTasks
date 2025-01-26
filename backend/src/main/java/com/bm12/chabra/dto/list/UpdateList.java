package com.bm12.chabra.dto.list;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UpdateList {

    @NotNull(message = "Id cannot be null")
    @Schema(description = "ID of the space", example = "example")
    private UUID id;
    @Schema(description = "Name of the space", example = "example")
    private String name;

    @Schema(description = "Description of the space", example = "example")
    private String description;

    @Schema(description = "Color of the space", example = "example")
    private String color;

    public UpdateList(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public UpdateList() {
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

