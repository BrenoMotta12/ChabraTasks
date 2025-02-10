package com.bm12.chabra.dto.tag;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UpdateTag {

    @NotNull(message = "Id is required")
    private UUID id;

    private String description;

    private String color;

    public UpdateTag(UUID id, String description, String color) {
        this.id = id;
        this.description = description;
        this.color = color;
    }
    public UpdateTag() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
