package com.bm12.chabra.controller;

import com.bm12.chabra.config.validation.FormException;
import com.bm12.chabra.dto.list.GetList;
import com.bm12.chabra.dto.list.SaveList;
import com.bm12.chabra.dto.list.UpdateList;
import com.bm12.chabra.dto.space.GetSpace;
import com.bm12.chabra.dto.space.SaveSpace;
import com.bm12.chabra.dto.space.UpdateSpace;
import com.bm12.chabra.model.Space;
import com.bm12.chabra.service.ListSevice;
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

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/list")
@Tag(name = "List")
public class ListController {


    private final ListSevice listSevice;

    public ListController(ListSevice listSevice) {
        this.listSevice = listSevice;
    }

    /**
     * Endpoint para cadastrar uma nova lista.
     *
     * @param saveList DTO contendo as informações da lista a ser cadastrada.
     * @return ResponseEntity contendo os dados da lista recém-cadastrada.
     */
    @PostMapping
    public ResponseEntity<GetList> create(@RequestBody @Valid SaveList saveList) {
        return this.listSevice.create(saveList);
    }


    /**
     * Endpoint para deletar uma lista.
     *
     * @param id UUID da lista a ser deletada.
     * @return String de confirmação.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return this.listSevice.delete(id);
    }

    /**
     * Endpoint para atualizar um espaço.
     *
     * @param updateList DTO contendo as informações do espaço a ser atualizado.
     * @return ResponseEntity contendo os dados da lista atualizado e o status HTTP 200 (OK).
     */

    @PutMapping()
    public ResponseEntity<GetList> update(@RequestBody @Valid UpdateList updateList) {
        return this.listSevice.update(updateList);
    }

    @GetMapping
    public ResponseEntity<List<GetList>> getAll() {
        return this.listSevice.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetList> getById(@PathVariable String id) {
        return this.listSevice.getById(id);
    }

}
