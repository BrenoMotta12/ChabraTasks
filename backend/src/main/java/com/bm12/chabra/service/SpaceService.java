package com.bm12.chabra.service;

import com.bm12.chabra.config.validation.NotFoundException;
import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.dto.space.UpdateSpace;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.model.User;
import com.bm12.chabra.repository.SpaceRepository;
import com.bm12.chabra.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    public SpaceService(SpaceRepository spaceRepository, UserRepository userRepository) {
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
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
            return ResponseEntity.created(URI.create("/space/" + space.getId())).body(new GetSpace(space));
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
            return ResponseEntity.ok(new GetSpace(space));
        } catch (Exception e) {
            throw new RuntimeException("Error updating space" + e);
        }
    }

    public ResponseEntity<List<GetSpace>> getAll() {

        org.springframework.security.core.userdetails.User userDetails = UserService.GetUserIfNotAdminOrModerator();


        if (userDetails != null) {
            /*
             * Se o userdetails for diferente de null, significa que o usuário tem somente permição "ROLE_USER", podendo acessar
             * somente os espaços em que existe uma tarefa que ela é responsável.
             * */
            User user = this.userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
            try {
                List<Space> spaces = this.spaceRepository.findSpacesByUserResponsible(user.getId());
                return ResponseEntity.ok(spaces.stream().map(GetSpace::new).toList());
            } catch (Exception e) {
                throw new RuntimeException("Error getting spaces" + e);
            }
        } else {
            /*
            *  Se o userdetails for null, o usuário é administrador ou moderador, podendo ver todos os espaços.
            * */
            try {
                List<Space> spaces = this.spaceRepository.findAll();
                return ResponseEntity.ok(spaces.stream().map(GetSpace::new).toList());
            } catch (Exception e) {
                throw new RuntimeException("Error getting spaces" + e);
            }
        }


    }

    public ResponseEntity<GetSpace> getById(String id) {
        try {
            Space space = this.spaceRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Space not found"));
            return ResponseEntity.ok(new GetSpace(space));
        } catch (Exception e) {
            throw new RuntimeException("Error getting space" + e);
        }
    }
}
