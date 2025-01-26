package com.bm12.chabra.dto.status;

import com.bm12.chabra.model.Status;
import com.bm12.chabra.model.enums.StatusType;

import java.util.UUID;

public class GetStatus {

    private UUID id;

    private String description;

    private String color;

    private StatusType statusType;


    public GetStatus(Status status) {
        this.id = status.getId();
        this.description = status.getDescription();
        this.statusType = status.getStatusType();
        this.color = status.getColor();
    }

    public GetStatus() {
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

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
