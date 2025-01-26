package com.bm12.chabra.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "list_tasks")
@Table(name = "list_tasks")
public class ListTask {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color", nullable = false)
    private String color;
    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @OneToMany(mappedBy = "listTask")
    private List<Task> tasks;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public ListTask(UUID id, String name, String description, String color, Space space) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.space = space;
    }

    public ListTask(String name, String description, String color, Space space) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.space = space;
    }

    public ListTask() {

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
