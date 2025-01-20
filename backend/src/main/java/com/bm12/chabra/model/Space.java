package com.bm12.chabra.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

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

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "space")
    private List<ListTask> list;


    public Space(UUID id, String name, String description, List<ListTask> list) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.list = list;
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
}
