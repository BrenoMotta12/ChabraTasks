package com.bm12.chabra.dto.status;

import com.bm12.chabra.model.enums.StatusType;
import jakarta.validation.constraints.NotEmpty;

public class SaveStatus {

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private String color;

    private StatusType statusType;

    public SaveStatus(String description, String color, StatusType statusType) {
        this.description = description;
        this.color = color;
        this.statusType = statusType;
    }

    public SaveStatus() {
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

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
