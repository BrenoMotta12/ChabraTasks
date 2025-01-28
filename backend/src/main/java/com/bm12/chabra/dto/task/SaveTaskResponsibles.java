package com.bm12.chabra.dto.task;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class SaveTaskResponsibles {

    @NotNull(message = "O id da tarefa é obrigatório")
    private UUID taskId;

    private List<UUID> responsibles;

    public SaveTaskResponsibles(UUID taskId, List<UUID> responsibles) {
        this.taskId = taskId;
        this.responsibles = responsibles;
    }

    public SaveTaskResponsibles() {
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public List<UUID> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<UUID> responsibles) {
        this.responsibles = responsibles;
    }
}
