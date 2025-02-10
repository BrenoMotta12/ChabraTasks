package com.bm12.chabra.service;

import com.bm12.chabra.dto.priority.GetPriority;
import com.bm12.chabra.model.Priority;
import com.bm12.chabra.repository.PriorityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository  priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public ResponseEntity<List<GetPriority>> getAll() {
        try {
            List<Priority> priorities = this.priorityRepository.findAll();
            return ResponseEntity.ok().body(priorities.stream().map(GetPriority::new).toList());
        } catch (Exception e) {
            throw new RuntimeException("Error getting all priorities" + e);
        }
    }

    public ResponseEntity<GetPriority> getById(UUID id) {

        try {
            Priority priority = this.priorityRepository.findById(id).orElseThrow(() -> new RuntimeException("Priority not found"));
            return ResponseEntity.ok().body(new GetPriority(priority));
        } catch (Exception e) {
            throw new RuntimeException("Error getting priority by id" + e);
        }
    }
}
