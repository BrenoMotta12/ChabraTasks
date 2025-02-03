package com.bm12.chabra.dto.task;

import com.bm12.chabra.dto.priority.GetPriority;
import com.bm12.chabra.dto.status.GetStatus;
import com.bm12.chabra.dto.user.GetUser;
import com.bm12.chabra.model.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GetTask {

    private UUID id;

    private String name;

    private String description;

    private LocalDate dueDate;

    private GetStatus status;

    private GetPriority priority;

    private List<GetUser> responsibles;

    private List<UUID> tags;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private UUID listTaskId;

    public GetTask(
            UUID id,
            String name,
            String description,
            LocalDate dueDate,
            GetStatus status,
            GetPriority priority,
            List<GetUser> responsibles,
            List<UUID> tags,
            LocalDateTime createdAt,
            LocalDateTime completedAt,
            UUID listTaskId)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
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
        this.status = task.getStatus() != null ? new GetStatus(task.getStatus()) : null;
        this.priority = task.getPriority() != null ? new GetPriority(task.getPriority()) : null;
        this.responsibles = task.getResponsibles() != null
                ? task.getResponsibles().stream().map(GetUser::new).toList()
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public GetStatus getStatus() {
        return status;
    }

    public void setStatus(GetStatus status) {
        this.status = status;
    }

    public GetPriority getPriority() {
        return priority;
    }

    public void setPriority(GetPriority priority) {
        this.priority = priority;
    }

    public List<GetUser> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<GetUser> responsibles) {
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
