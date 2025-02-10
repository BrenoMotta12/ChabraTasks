package com.bm12.chabra.controller;

import com.bm12.chabra.dto.priority.GetPriority;
import com.bm12.chabra.service.PriorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }


    @GetMapping
    public ResponseEntity<List<GetPriority>> getAll() {
        return this.priorityService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPriority> get(@PathVariable UUID id) {
        return this.priorityService.getById(id);
    }

}
