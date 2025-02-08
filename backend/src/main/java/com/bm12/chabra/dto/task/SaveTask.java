package com.bm12.chabra.dto.task;

import com.bm12.chabra.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SaveTask {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    private UUID statusId;

    private UUID priorityId;

    @NotNull(message = "List ID cannot be null")
    private UUID listId;

    private List<UUID> responsibles;
    private List<UUID> tags;

    public SaveTask(String name, String description, LocalDate dueDate, UUID statusId, UUID priorityId, UUID listId, List<UUID> responsibles, List<UUID> tags) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.statusId = statusId;
        this.priorityId = priorityId;
        this.listId = listId;
        this.responsibles = responsibles;
        this.tags = tags;
    }

    public SaveTask () {}

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

    public UUID getListId() {
        return listId;
    }

    public void setListId(UUID listId) {
        this.listId = listId;
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

    public List<UUID> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<UUID> responsibles) {
        this.responsibles = responsibles;
    }

    public List<UUID> getTags() {
        return tags;
    }

    public void setTags(List<UUID> tags) {
        this.tags = tags;
    }
}
