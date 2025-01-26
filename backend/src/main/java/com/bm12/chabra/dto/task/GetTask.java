package com.bm12.chabra.dto.task;

import com.bm12.chabra.model.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GetTask {

    private UUID id;

    private String name;


    private String description;

    private Date dueDate;

    private UUID statusId;

    private UUID priorityId;

    private List<UUID> responsibles;

    private List<UUID> tags;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private UUID listTaskId;

    public GetTask(UUID id, String name, String description, Date dueDate, UUID statusId, UUID priorityId, List<UUID> responsibles, List<UUID> tags, LocalDateTime createdAt, LocalDateTime completedAt, UUID listTaskId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.statusId = statusId;
        this.priorityId = priorityId;
        this.responsibles = responsibles;
        this.tags = tags;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.listTaskId = listTaskId;
    }

    public GetTask(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.statusId = task.getStatus() != null ? task.getStatus().getId() : null;
        this.priorityId = task.getPriority() != null ? task.getPriority().getId() : null;
        this.responsibles = task.getResponsibles() != null
                ? task.getResponsibles().stream().map(User::getId).toList()
                : List.of();
        this.tags = task.getTags() != null
                ? task.getTags().stream().map(Tag::getId).toList()
                : List.of();
        this.createdAt = task.getCreatedAt();
        this.completedAt = task.getCompletedAt();
        this.listTaskId = task.getListTask() != null ? task.getListTask().getId() : null;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public UUID getListTaskId() {
        return listTaskId;
    }

    public void setListTaskId(UUID listTaskId) {
        this.listTaskId = listTaskId;
    }
}
