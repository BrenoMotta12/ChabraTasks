package com.bm12.chabra.controller;

import com.bm12.chabra.dto.priority.GetPriority;
import com.bm12.chabra.dto.priority.SavePriority;
import com.bm12.chabra.dto.priority.UpdatePriority;
import com.bm12.chabra.service.PriorityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }


    /**
     * Cria uma nova prioridade
     *
     * @param savePriority
     * @return ResponseEntity<GetStatus>
     * */
    @PostMapping
    public ResponseEntity<GetPriority> create(@Valid @RequestBody SavePriority savePriority) {
        return this.priorityService.create(savePriority);
    }

    /**
     * Atualiza uma prioridade
     *
     * @param updatePriority
     * @return ResponseEntity<GetStatus>
     * */
    @PutMapping
    public ResponseEntity<GetPriority> update(@Valid @RequestBody UpdatePriority updatePriority) {
        return this.priorityService.update(updatePriority);
    }

    /**
     * Deleta uma prioridade
     *
     * @param id
     * @return ResponseEntity<String>
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        return this.priorityService.delete(id);
    }
}
