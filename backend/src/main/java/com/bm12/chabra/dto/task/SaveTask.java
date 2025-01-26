package com.bm12.chabra.dto.task;

import com.bm12.chabra.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SaveTask {


    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String description;

    private Date dueDate;

    private UUID statusId;

    private UUID priorityId;

    @NotNull(message = "List ID cannot be null")
    private UUID listId;

    public SaveTask(String name, String description, Date dueDate, UUID statusId, UUID priorityId, UUID listId) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.statusId = statusId;
        this.priorityId = priorityId;
        this.listId = listId;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
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
}
