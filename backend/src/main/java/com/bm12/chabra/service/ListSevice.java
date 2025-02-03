package com.bm12.chabra.service;

import com.bm12.chabra.config.validation.NotFoundException;
import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.dto.list.SaveList;
import com.bm12.chabra.dto.list.UpdateList;
import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.model.ListTask;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.model.User;
import com.bm12.chabra.repository.ListRepository;
import com.bm12.chabra.repository.SpaceRepository;
import com.bm12.chabra.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@Service
public class ListSevice {

    private final ListRepository listRepository;
    private final UserRepository userRepository;

    private final SpaceRepository spaceRepository;

    public ListSevice(ListRepository listRepository, UserRepository userRepository, SpaceRepository spaceRepository) {
        this.listRepository = listRepository;
        this.userRepository = userRepository;
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

    public ResponseEntity<List<GetList>> getAll() {

        org.springframework.security.core.userdetails.User userDetails = UserService.GetUserIfNotAdminOrModerator();

        if (userDetails != null) {
            /*
             * Se o userdetails for diferente de null, significa que o usuário tem somente permição "ROLE_USER", podendo acessar
             * somente os espaços em que existe uma tarefa que ela é responsável.
             * */
            User user = this.userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
            try {
                List<ListTask> listTasks = this.listRepository.findListByUserResponsible(user.getId());
                return ResponseEntity.ok(listTasks.stream().map(GetList::new).toList());
            } catch (Exception e) {
                throw new RuntimeException("Error getting spaces" + e);
            }
        } else {
            try {
                List<ListTask> lists = this.listRepository.findAll();
                return ResponseEntity.ok(lists.stream().map(GetList::new).toList());
            } catch (Exception e) {
                throw new RuntimeException("Error getting lists" + e);
            }
        }
    }

    public ResponseEntity<GetList> getById(String id) {
        try {
            ListTask listTask = this.listRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("List not found"));
            return ResponseEntity.ok(new GetList(listTask));
        } catch (Exception e) {
            throw new RuntimeException("Error getting list" + e);
        }
    }
}
