package com.bm12.chabra.dto.priority;

import com.bm12.chabra.model.enums.PriorityLevel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SavePriority {

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private String color;

    @NotNull(message = "Priority level type cannot be empty")
    private PriorityLevel priorityLevel;

    public SavePriority(String description, String color, PriorityLevel priorityLevel) {
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

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}
