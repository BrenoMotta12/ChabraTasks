package com.bm12.chabra.service;

import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.dto.status.GetStatus;
import com.bm12.chabra.dto.status.SaveStatus;
import com.bm12.chabra.dto.status.UpdateStatus;
import com.bm12.chabra.model.Status;
import com.bm12.chabra.repository.StatusRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public ResponseEntity<GetStatus> create(SaveStatus saveStatus) {

        try {
            Status status = new Status(saveStatus);
            status = this.statusRepository.save(status);
            return ResponseEntity.created(URI.create("/status/" + status.getId())).body(new GetStatus(status));
        } catch (Exception e) {
            throw new RuntimeException("Error saving status" + e);
        }


    }

    public ResponseEntity<GetStatus> update(UpdateStatus updateStatus) {

        Status status = this.statusRepository.findById(updateStatus.getId()).orElseThrow(() -> new RuntimeException("Status not found"));

        try {
            status.setDescription(updateStatus.getDescription() != null ? updateStatus.getDescription() : status.getDescription());
            status.setColor(updateStatus.getColor() != null ? updateStatus.getColor() : status.getColor());
            status = this.statusRepository.save(status);
            return ResponseEntity.ok().body(new GetStatus(status));
        } catch (Exception e) {
            throw new RuntimeException("Error updating status" + e);
        }
    }

    public ResponseEntity<String> delete(UUID id) {

        Status status = this.statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Status not found"));

        try {
            this.statusRepository.delete(status);
            return ResponseEntity.ok().body("Status deleted");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting status" + e);
        }
    }
}
