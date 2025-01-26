package com.bm12.chabra.service;

import com.bm12.chabra.config.validation.AlreadyExistsException;
import com.bm12.chabra.config.validation.NotFoundException;
import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.dto.space.UpdateSpace;
import com.bm12.chabra.dto.user.GetUser;
import com.bm12.chabra.dto.user.SaveUser;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.model.User;
import com.bm12.chabra.model.enums.UserRole;
import com.bm12.chabra.repository.SpaceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class SpaceService {

    private final SpaceRepository spaceRepository;

    public SpaceService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    public ResponseEntity<GetSpace> create(SaveSpace saveSpace) {
        Space space = new Space(
                saveSpace.getName(),
                saveSpace.getDescription(),
                saveSpace.getColor()
        );
        try {
            // Cria um novo Espaço
            space = this.spaceRepository.save(space);
            return ResponseEntity.created(URI.create("/space/" + space.getId())).body(GetSpace.converter(space));
        } catch (Exception e) {
            throw new RuntimeException("Error creating space");
        }
    }

    public ResponseEntity<String> delete(UUID id) {

        // Verifica se o Espaço existe
        Space space = this.spaceRepository.findById(id).orElseThrow(() -> new NotFoundException("Space not found"));

        try {
            space.setDeletedAt(java.time.LocalDateTime.now());
            this.spaceRepository.save(space);
            return ResponseEntity.ok("Space deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting space" + e);
        }
    }

    public ResponseEntity<GetSpace> update(UpdateSpace updateSpace) {

        // Verifica se o espaço existe
        Space space = this.spaceRepository.findById(updateSpace.getId()).orElseThrow(() -> new NotFoundException("Space not found"));

        try {
            space.setName(updateSpace.getName() != null ? updateSpace.getName(): space.getName());
            space.setDescription(updateSpace.getDescription() != null ? updateSpace.getDescription(): space.getDescription());
            space.setColor(updateSpace.getColor() != null ? updateSpace.getColor(): space.getColor());
            space = this.spaceRepository.save(space);
            return ResponseEntity.ok(GetSpace.converter(space));
        } catch (Exception e) {
            throw new RuntimeException("Error updating space" + e);
        }
    }

    public ResponseEntity<List<Space>> getAll() {

        try {
            return ResponseEntity.ok(this.spaceRepository.findAll());
        } catch (Exception e) {
            throw new RuntimeException("Error getting spaces" + e);
        }
    }
}
