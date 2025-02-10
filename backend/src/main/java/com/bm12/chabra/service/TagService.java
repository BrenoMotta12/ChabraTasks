package com.bm12.chabra.service;

import com.bm12.chabra.dto.status.GetStatus;
import com.bm12.chabra.dto.tag.GetTag;
import com.bm12.chabra.dto.tag.SaveTag;
import com.bm12.chabra.dto.tag.UpdateTag;
import com.bm12.chabra.model.Tag;
import com.bm12.chabra.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    public ResponseEntity<GetTag> create(SaveTag saveTag) {
        try {
            Tag tag = new Tag(saveTag);
            tag = this.tagRepository.save(tag);
            return ResponseEntity.created(URI.create("/tag/" + tag.getId())).body(new GetTag(tag));
        } catch (Exception e) {
            throw new RuntimeException("Error creating tag" + e);
        }
    }

    public ResponseEntity<GetTag> update(UpdateTag updateTag) {
        Tag tag = this.tagRepository.findById(updateTag.getId()).orElseThrow(() -> new RuntimeException("Tag not found"));

        try{
            tag.update(updateTag);
            tag = this.tagRepository.save(tag);
            return ResponseEntity.ok().body(new GetTag(tag));
        } catch (Exception e) {
            throw new RuntimeException("Error updating tag" + e);
        }
    }

    public ResponseEntity<GetTag> getById(UUID id) {
        Tag tag = this.tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));

        try {
            return ResponseEntity.ok().body(new GetTag(tag));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching tag" + e);
        }
    }

    public ResponseEntity<List<GetTag>> getAll() {
        try {
            List<Tag> tagList = this.tagRepository.findAll();
            return ResponseEntity.ok().body(tagList.stream().map(GetTag::new).toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching tags" + e);
        }
    }

    public ResponseEntity<String> delete(UUID id) {
        Tag tag = this.tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));

        try {
            this.tagRepository.delete(tag);
            return ResponseEntity.ok().body("Tag deleted");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting tag" + e);
        }
    }
}
