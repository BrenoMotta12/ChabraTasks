package com.bm12.chabra.dto.priority;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UpdatePriority {

    @NotNull(message = "Id is required")
    private UUID id;
    private String description;
    private String color;

    private Integer priorityLevel;

    public UpdatePriority(UUID id, String description, String color, Integer priorityLevel) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.priorityLevel = priorityLevel;
    }

    public UpdatePriority() {
    }

    public UUID getId() {
        return id;
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