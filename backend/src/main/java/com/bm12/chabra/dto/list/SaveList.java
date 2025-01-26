package com.bm12.chabra.dto.list;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class SaveList {


    @Schema(description = "Name of the space", example = "example")
    private String name;
    @Schema(description = "Description of the space", example = "example")
    private String description;
    @Schema(description = "Color of the space", example = "example")
    private String color;

    @NotNull(message = "Space ID cannot be null")
    @Schema(description = "Space ID", example = "example")
    private UUID spaceId;

    public SaveList(String name, String description, String color, UUID spaceId) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.spaceId = spaceId;
    }

    public SaveList() {
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

    public UUID getSpaceId() {
        return spaceId;
    }

}
