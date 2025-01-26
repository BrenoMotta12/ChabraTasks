package com.bm12.chabra.dto.priority;

import com.bm12.chabra.model.Priority;
import com.bm12.chabra.model.Status;
import com.bm12.chabra.model.enums.StatusType;

import java.util.UUID;

public class GetPriority {

    private UUID id;

    private String description;

    private String color;

    private Integer priorityLevel;


    public GetPriority(Priority priority) {
        this.id = priority.getId();
        this.description = priority.getDescription();
        this.priorityLevel = priority.getPriorityLevel();
        this.color = priority.getColor();
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
