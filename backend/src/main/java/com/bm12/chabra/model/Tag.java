package com.bm12.chabra.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity(name = "tag")
@Table(name = "tag")
public class Tag {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = false)
    private String color;

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;

    public Tag(UUID id, String description, String color, List<Task> tasks) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.tasks = tasks;
    }

    public Tag() {}

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
