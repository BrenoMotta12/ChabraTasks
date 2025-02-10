package com.bm12.chabra.controller;

import com.bm12.chabra.dto.tag.GetTag;
import com.bm12.chabra.dto.tag.SaveTag;
import com.bm12.chabra.dto.tag.UpdateTag;
import com.bm12.chabra.service.TagService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    /**
     * Endpoint para criar uma nova tag
     *
     * @param saveTag
     * @return ResponseEntity<GetTag>
     * */
    @PostMapping
    public ResponseEntity<GetTag> create(@Valid @RequestBody SaveTag saveTag) {
        return this.tagService.create(saveTag);
    }

    /**
     * Endpoint para atualizar uma tag
     *
     * @param updateTag
     * @return ResponseEntity<GetTag>
     * */
    @PutMapping
    public ResponseEntity<GetTag> update(@Valid @RequestBody UpdateTag updateTag) {
        return this.tagService.update(updateTag);
    }

    /**
     * Endpoint para buscar uma tag pelo id
     *
     * @param id
     * @return ResponseEntity<GetTag>
     * */
    @GetMapping("/{id}")
    public ResponseEntity<GetTag> getById(@PathVariable("id") UUID id) {
        return this.tagService.getById(id);
    }

    /**
     * Endpoint para deletar uma tag pelo id
     *
     * @param id
     * @return ResponseEntity<String>
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        return this.tagService.delete(id);
    }

    /**
     * Endpoint para buscar todas as tags
     *
     * @return ResponseEntity<List<GetTag>>
     * */
    @GetMapping
    public ResponseEntity<List<GetTag>> getAll() {
        return this.tagService.getAll();
    }
}
