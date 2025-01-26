package com.bm12.chabra.dto.priority;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SavePriority {

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private String color;

    @NotNull(message = "Priority level type cannot be empty")
    private Integer priorityLevel;

    public SavePriority(String description, String color, Integer priorityLevel) {
        this.description = description;
        this.color = color;
        this.priorityLevel = priorityLevel;
    }

    public SavePriority() {
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

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}
