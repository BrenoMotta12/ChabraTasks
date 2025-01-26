package com.bm12.chabra.model;

import com.bm12.chabra.model.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.List;
import java.util.UUID;

@Entity(name = "status")
@Table(name = "status")
public class Status {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = true)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type", nullable = false)
    private StatusType statusType;

    @OneToMany(mappedBy = "status")
    private List<Task> tasks;


    public Status(UUID id, String description, String color, StatusType statusType, List<Task> tasks) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.statusType = statusType;
        this.tasks = tasks;
    }

    public Status(String color,String description, StatusType statusType) {
        this.color = color;
        this.description = description;
        this.statusType = statusType;
    }

    public Status() {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
