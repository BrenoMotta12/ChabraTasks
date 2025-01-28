package com.bm12.chabra.service;

import com.bm12.chabra.config.validation.NotFoundException;
import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.dto.list.SaveList;
import com.bm12.chabra.dto.list.UpdateList;
import com.bm12.chabra.model.ListTask;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.repository.ListRepository;
import com.bm12.chabra.repository.SpaceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;


@Service
public class ListSevice {

    private final ListRepository listRepository;

    private final SpaceRepository spaceRepository;

    public ListSevice(ListRepository listRepository, SpaceRepository spaceRepository) {
        this.listRepository = listRepository;
        this.spaceRepository = spaceRepository;
    }

    public ResponseEntity<GetList> create(SaveList saveList) {
        Space space = this.spaceRepository.findById(saveList.getSpaceId()).orElseThrow(() -> new NotFoundException("Space not found"));

        ListTask listTask = new ListTask(
                saveList.getName(),
                saveList.getDescription(),
                saveList.getColor(),
                space
        );
        try {
            // Cria um novo Espaço
            listTask = this.listRepository.save(listTask);
            return ResponseEntity.created(URI.create("/list/" + listTask.getId())).body(new GetList(listTask));
        } catch (Exception e) {
            throw new RuntimeException("Error creating list");
        }
    }

    public ResponseEntity<String> delete(UUID id) {

        // Verifica se o Espaço existe
        ListTask listTask = this.listRepository.findById(id).orElseThrow(() -> new NotFoundException("Space not found"));

        try {
            listTask.setDeletedAt(java.time.LocalDateTime.now());
            this.listRepository.save(listTask);
            return ResponseEntity.ok("List deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting list" + e);
        }
    }

    public ResponseEntity<GetList> update(UpdateList updateList) {

        // Verifica se o espaço existe
        ListTask listTask = this.listRepository.findById(updateList.getId()).orElseThrow(() -> new NotFoundException("List not found"));

        try {
            listTask.setName(updateList.getName() != null ? updateList.getName(): listTask.getName());
            listTask.setDescription(updateList.getDescription() != null ? updateList.getDescription(): listTask.getDescription());
            listTask.setColor(updateList.getColor() != null ? updateList.getColor(): listTask.getColor());
            listTask = this.listRepository.save(listTask);
            return ResponseEntity.ok(new GetList(listTask));
        } catch (Exception e) {
            throw new RuntimeException("Error updating list" + e);
        }
    }
}
