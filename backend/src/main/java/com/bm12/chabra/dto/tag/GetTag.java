package com.bm12.chabra.dto.tag;

import com.bm12.chabra.model.Tag;
import jakarta.persistence.Column;

import java.util.UUID;

public class GetTag {

    private UUID id;

    private String description;

    private String color;

    public GetTag(Tag tag) {
        this.id = tag.getId();
        this.description = tag.getDescription();
        this.color = tag.getColor();
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
