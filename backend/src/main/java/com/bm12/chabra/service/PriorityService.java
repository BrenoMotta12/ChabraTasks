package com.bm12.chabra.service;

import com.bm12.chabra.dto.priority.GetPriority;
import com.bm12.chabra.dto.priority.SavePriority;
import com.bm12.chabra.dto.priority.UpdatePriority;
import com.bm12.chabra.dto.status.GetStatus;
import com.bm12.chabra.model.Priority;
import com.bm12.chabra.repository.PriorityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository  priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public ResponseEntity<GetPriority> create(SavePriority savePriority) {

        try {
            Priority priority = new Priority(savePriority);
            priority = this.priorityRepository.save(priority);
            return ResponseEntity.created(URI.create("/priority/" + priority.getId())).body(new GetPriority(priority));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    public ResponseEntity<GetPriority> update(UpdatePriority updatePriority) {
        Priority priority = this.priorityRepository.findById(updatePriority.getId()).orElseThrow(() -> new RuntimeException("Priority not found"));

        try {

            priority.update(updatePriority);
            priority = this.priorityRepository.save(priority);
            return ResponseEntity.ok().body(new GetPriority(priority));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    public ResponseEntity<String> delete(UUID id) {
        Priority priority = this.priorityRepository.findById(id).orElseThrow(() -> new RuntimeException("Priority not found"));

        try {
            this.priorityRepository.delete(priority);
            return ResponseEntity.ok().body("Priority deleted");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting priority" + e);
        }

    }
}
