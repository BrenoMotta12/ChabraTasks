package com.bm12.chabra.dto.list;

import com.bm12.chabra.model.ListTask;
import com.bm12.chabra.model.Space;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class GetList {


    private UUID id;

    private UUID spaceId;

    private String name;


    private String description;


    private String color;

    public GetList(UUID id, String name, String description, String color, UUID spaceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.spaceId = spaceId;
    }

    public GetList(ListTask listTask) {
        this.id = listTask.getId();
        this.spaceId = listTask.getSpace().getId();
        this.name = listTask.getName();
        this.description = listTask.getDescription();
        this.color = listTask.getColor();
    }

    public GetList() {
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setSpaceId(UUID spaceId) {
        this.spaceId = spaceId;
    }
}


