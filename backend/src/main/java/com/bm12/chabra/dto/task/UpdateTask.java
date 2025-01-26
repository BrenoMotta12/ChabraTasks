package com.bm12.chabra.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class UpdateTask {

    @NotNull
    private UUID id;

    private String name;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    private UUID statusId;

    private UUID priorityId;


    public UpdateTask(UUID id, String name, String description, LocalDate dueDate, UUID statusId, UUID priorityId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.statusId = statusId;
        this.priorityId = priorityId;

    }

    public UpdateTask() {
    }

    public UUID getId() {
        return id;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public UUID getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(UUID priorityId) {
        this.priorityId = priorityId;
    }

}
