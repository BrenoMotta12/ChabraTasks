package com.bm12.chabra.controller;

import com.bm12.chabra.config.validation.FormException;
import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.dto.list.SaveList;
import com.bm12.chabra.dto.task.*;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.model.Task;
import com.bm12.chabra.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
@Tag(name = "Task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint para cadastrar uma nova tarefa.
     *
     * @param saveTask DTO contendo as informações da lista a ser cadastrada.
     * @return ResponseEntity contendo os dados da lista recém-cadastrada.
     */
    @PostMapping
    public ResponseEntity<GetTask> create(@RequestBody @Valid SaveTask saveTask) {
        return this.taskService.create(saveTask);
    }

    /**
     * Endpoint setar os responsáveis pela tarefa.
     *
     * @param saveTaskResponsibles DTO contendo as informações da tarefas e usuários a serem setados como responsáveis
     * @return ResponseEntity contendo os novos responsáveis pela tarefa.
     */
    @PostMapping("/responsibles")
    public ResponseEntity<GetTask> setResposibles(@RequestBody @Valid SaveTaskResponsibles saveTaskResponsibles) {
        return this.taskService.setResponsibles(saveTaskResponsibles);
    }

    /**
     * Endpoint para cadastrar uma nova tarefa.
     *
     * @param id da tarefa a ser deletada.
     * @return ResponseEntity com o HHTP ok.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return this.taskService.delete(id);
    }

    /**
     * Endpoint para atualizar uma tarefa existente.
     *
     * @param updateTask DTO contendo as informações da tarefa a ser atualizada.
     * @return ResponseEntity contendo os dados da tarefa atualizada e o status HTTP 200 (OK).
     * */
    @PutMapping
    public ResponseEntity<GetTask> update(@Valid @RequestBody UpdateTask updateTask) {
        return this.taskService.update(updateTask);
    }

}
