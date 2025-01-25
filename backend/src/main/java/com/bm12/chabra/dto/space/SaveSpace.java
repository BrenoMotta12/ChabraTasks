package com.bm12.chabra.dto.space;

import com.bm12.chabra.model.ListTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class SaveSpace {

    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "Name of the space", example = "example")
    private String name;
    @Schema(description = "Description of the space", example = "example")
    private String description;
    @Schema(description = "Color of the space", example = "example")
    private String color;

    public SaveSpace(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public SaveSpace() {
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
}
