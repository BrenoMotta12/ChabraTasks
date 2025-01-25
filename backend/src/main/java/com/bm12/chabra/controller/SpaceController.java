package com.bm12.chabra.controller;

import com.bm12.chabra.config.validation.FormException;
import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.dto.space.UpdateSpace;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.service.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Create a new space", description = "Creates a new space in the system.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully registered",
                    content = {@Content(schema = @Schema(implementation = Space.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration data",
                    content = @Content(schema = @Schema(implementation = FormException.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
    })
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
    @Operation(summary = "Delete a space", description = "Delete space in the system.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Space deleted successfully",
                    content = {@Content(schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration data",
                    content = @Content(schema = @Schema(implementation = FormException.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
    })
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
    @Operation(summary = "Update a space", description = "update a space in the system.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Space updated successfully",
                    content = {@Content(schema = @Schema(implementation = Space.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration data",
                    content = @Content(schema = @Schema(implementation = FormException.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(type = ""))
            ),
    })
    @PutMapping()
    public ResponseEntity<GetSpace> update(@RequestBody @Valid UpdateSpace updateSpace) {
        return this.spaceService.update(updateSpace);
    }


}
