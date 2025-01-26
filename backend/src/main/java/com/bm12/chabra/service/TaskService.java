package com.bm12.chabra.service;

import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.dto.task.GetTask;
import com.bm12.chabra.dto.task.SaveTask;
import com.bm12.chabra.model.*;
import com.bm12.chabra.model.enums.StatusType;
import com.bm12.chabra.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRespository taskRespository;
    private final ListRepository  listRepository;
    private final StatusRepository statusRepository;
    private  final TagRepository tagRepository;
    private final PriorityRepository priorityRepository;

    public TaskService(TaskRespository taskRespository, ListRepository listRepository, StatusRepository statusRepository, TagRepository tagRepository, PriorityRepository priorityRepository) {
        this.taskRespository = taskRespository;
        this.listRepository = listRepository;
        this.statusRepository = statusRepository;
        this.tagRepository = tagRepository;
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

}