package com.bm12.chabra.dto.tag;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class SaveTag {

    @NotEmpty(message = "Description is required")
    private String description;

    private String color;

    public SaveTag(String description, String color) {
        this.description = description;
        this.color = color;
    }

    public SaveTag() {
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
