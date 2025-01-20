package com.bm12.chabra.model;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.List;
import java.util.UUID;

@Entity(name = "priority")
@Table(name = "priority")
public class Priority {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "priority_level", nullable = false)
    private Integer priorityLevel;

    @OneToMany(mappedBy = "priority")
    private List<Task> tasks;

    public Priority(UUID id, String description, String color, Integer priorityLevel, List<Task> tasks) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.priorityLevel = priorityLevel;
        this.tasks = tasks;
    }

    public Priority() {
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

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
