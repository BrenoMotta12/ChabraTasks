package com.bm12.chabra.controller;

import com.bm12.chabra.dto.status.GetStatus;
import com.bm12.chabra.dto.status.SaveStatus;
import com.bm12.chabra.dto.status.UpdateStatus;
import com.bm12.chabra.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }


    /**
     * Cria um novo status
     *
     * @param saveStatus
     * @return ResponseEntity<GetStatus>
     * */
    @PostMapping
    public ResponseEntity<GetStatus> create(@Valid @RequestBody SaveStatus saveStatus) {
        return this.statusService.create(saveStatus);
    }

    /**
     * Atualiza um status
     *
     * @param updateStatus
     * @return ResponseEntity<GetStatus>
     * */
    @PutMapping
    public ResponseEntity<GetStatus> update(@Valid @RequestBody UpdateStatus updateStatus) {
        return this.statusService.update(updateStatus);
    }

    /**
     * Deleta um status
     *
     * @param id
     * @return ResponseEntity<String>
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        return this.statusService.delete(id);
    }
}
