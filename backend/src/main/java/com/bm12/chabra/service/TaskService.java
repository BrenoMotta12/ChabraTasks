package com.bm12.chabra.service;

import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.dto.task.GetTask;
import com.bm12.chabra.dto.task.SaveTask;
import com.bm12.chabra.dto.task.SaveTaskResponsibles;
import com.bm12.chabra.dto.task.UpdateTask;
import com.bm12.chabra.model.*;
import com.bm12.chabra.model.enums.StatusType;
import com.bm12.chabra.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRespository taskRespository;
    private final ListRepository  listRepository;
    private final StatusRepository statusRepository;
    private final TagRepository tagRepository;

    private final UserRepository userRepository;
    private final PriorityRepository priorityRepository;

    public TaskService(TaskRespository taskRespository, ListRepository listRepository, StatusRepository statusRepository, TagRepository tagRepository, UserRepository userRepository, PriorityRepository priorityRepository) {
        this.taskRespository = taskRespository;
        this.listRepository = listRepository;
        this.statusRepository = statusRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.priorityRepository = priorityRepository;
    }

    public ResponseEntity<GetTask> create(SaveTask saveTask) {

        ListTask listTask = this.listRepository.findById(saveTask.getListId()).orElseThrow(() -> new RuntimeException("List not found"));
        Status status = this.statusRepository.findAll().stream().filter(s -> s.getStatusType().equals(StatusType.NOT_STARTED)).findFirst().orElseThrow(() -> new RuntimeException("Status not found"));

        Priority priority = null;
        if(saveTask.getPriorityId() != null) {
            priority = this.priorityRepository.findById(saveTask.getPriorityId()).orElseThrow(() -> new RuntimeException("Priority not found"));
        }


        try {
            Task task = new Task(
                    saveTask.getName() != null ? saveTask.getName() : "Nova tarefa",
                    saveTask.getDescription(),
                    saveTask.getDueDate(),
                    status,
                    priority,
                    LocalDateTime.now(),
                    listTask
            );
            this.taskRespository.save(task);
            return ResponseEntity.created(URI.create("/task/" + task.getId())).body(new GetTask(task));
        } catch (Exception e) {
            throw new RuntimeException("Error creating task" + e);
        }


    }


    public ResponseEntity<String> delete(String id) {
        Task task = this.taskRespository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Task not found"));
        try {
            this.taskRespository.delete(task);
            return ResponseEntity.ok().body("Task deleted");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting task" + e);
        }
    }

    public ResponseEntity<GetTask> update(UpdateTask updateTask) {

        // Busca a task
        Task task = this.taskRespository.findById(updateTask.getId()).orElseThrow(() -> new RuntimeException("Task not found"));

        // Verifica se o usuario passou a prioridade
        if (updateTask.getPriorityId() != null) {
            // Se a prioridade existir no banco de dados, seta a memsa na task
            Priority priority = this.priorityRepository.findById(updateTask.getPriorityId()).orElseThrow(() -> new RuntimeException("Priority not found"));
            task.setPriority(priority);
        }

        // Verifica de o usuario passou o status
        if (updateTask.getStatusId() != null) {
            // Se o status existir no banco de dados, seta o mesmo na task
            Status status = this.statusRepository.findById(updateTask.getStatusId()).orElseThrow(() -> new RuntimeException("Status not found"));
            task.setStatus(status);
        }


        try {
            task.setName(updateTask.getName() != null ? updateTask.getName() : task.getName());
            task.setDescription(updateTask.getDescription() != null ? updateTask.getDescription() : task.getDescription());
            task.setDueDate(updateTask.getDueDate() != null ? updateTask.getDueDate() : task.getDueDate());
            this.taskRespository.save(task);
            return ResponseEntity.ok().body(new GetTask(task));
        } catch (Exception e) {
            throw new RuntimeException("Error updating task" + e);
        }

    }

    public ResponseEntity<GetTask> setResponsibles(SaveTaskResponsibles saveTaskResponsibles) {

        // Verifica se a task existe
        Task task = this.taskRespository.findById(saveTaskResponsibles.getTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));

        // Se os usuarios responsaveis forem informados, verifica a existencia deles e adiciona na lista.
        List<User> users = new ArrayList<>(List.of());
        if (saveTaskResponsibles.getResponsibles() != null) {
            for (UUID userId : saveTaskResponsibles.getResponsibles()) {
                User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                users.add(user);
            }
        }

        try {
            task.setResponsibles(users);
            this.taskRespository.save(task);
            return ResponseEntity.ok().body(new GetTask(task));
        } catch (Exception e) {
            throw new RuntimeException("Error updating task" + e);
        }

    }
}
