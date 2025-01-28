package com.bm12.chabra.controller;

import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.dto.space.UpdateSpace;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.service.SpaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/space")
@Tag(name = "Space")
public class SpaceController {

    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    /**
     * Endpoint para cadastrar um novo espaço.
     *
     * @param saveSpace DTO contendo as informações do espaço a ser cadastrado.
     * @return ResponseEntity contendo os dados do espaço recém-cadastrado.
     */

    @PostMapping
    public ResponseEntity<GetSpace> create(@RequestBody @Valid SaveSpace saveSpace) {
        return this.spaceService.create(saveSpace);
    }


    /**
     * Endpoint para deletar um espaço.
     *
     * @param id UUID do espaço a ser deletado.
     * @return String de confirmação.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return this.spaceService.delete(id);
    }

    /**
     * Endpoint para atualizar um espaço.
     *
     * @param updateSpace DTO contendo as informações do espaço a ser atualizado.
     * @return ResponseEntity contendo os dados do espaço atualizado e o status HTTP 200 (OK).
     */
    @PutMapping()
    public ResponseEntity<GetSpace> update(@RequestBody @Valid UpdateSpace updateSpace) {
        return this.spaceService.update(updateSpace);
    }

    /**
     * Endpoint obter os espaços.
     *
     * @return ResponseEntity contendo os dados dos espaços e o status HTTP 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<GetSpace>> getAll() {
        return this.spaceService.getAll();
    }

}
