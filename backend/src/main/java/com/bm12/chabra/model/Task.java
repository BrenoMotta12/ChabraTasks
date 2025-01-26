package com.bm12.chabra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "task")
@Table(name = "task")
public class Task {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> responsibles;

    @ManyToMany
    @JoinTable(
            name = "task_tag",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "list_task_id")
    private ListTask listTask;


    public Task(
            UUID id,
            String name,
            String description,
            LocalDate dueDate,
            Status status,
            Priority priority,
            List<User> responsibles,
            List<Tag> tags,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {
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
    }

    public Task(
            String name,
            String description,
            LocalDate dueDate,
            Status status,
            Priority priority,
            LocalDateTime createdAt,
            ListTask listTask)
    {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.listTask = listTask;
    }

    public Task() {}

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<User> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<User> responsibles) {
        this.responsibles = responsibles;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
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

    public ListTask getListTask() {
        return listTask;
    }

    public void setListTask(ListTask listTask) {
        this.listTask = listTask;
    }
}
