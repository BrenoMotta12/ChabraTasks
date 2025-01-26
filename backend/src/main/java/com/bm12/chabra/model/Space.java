package com.bm12.chabra.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "space")
@Table(name = "space")
public class Space {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "space")
    private List<ListTask> list;

    @Column(name = "delete_at")
    private LocalDateTime deletedAt;


    public Space(UUID id, String name, String description, String color, List<ListTask> list) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.list = list;
    }

    public Space(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public Space() {
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

    public List<ListTask> getList() {
        return list;
    }

    public void setList(List<ListTask> list) {
        this.list = list;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
