package com.bm12.chabra.dto.space;

import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class GetSpace {

    @Schema(description = "ID of the space", example = "example")
    private UUID id;
    @Schema(description = "Name of the space", example = "example")
    private String name;

    @Schema(description = "Description of the space", example = "example")
    private String description;

    @Schema(description = "Color of the space", example = "example")
    private String color;

    private List<GetList> lists;


    public GetSpace(UUID id, String name, String description, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public GetSpace(Space space) {
        this.id = space.getId();
        this.name = space.getName();
        this.description = space.getDescription();
        this.color = space.getColor();
        this.lists = space.getList().stream().map(GetList::new).toList();
    }


    public GetSpace() {
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

    public List<GetList> getLists() {
        return lists;
    }

    public void setLists(List<GetList> lists) {
        this.lists = lists;
    }
}


