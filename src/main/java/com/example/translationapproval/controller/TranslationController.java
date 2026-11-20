
package com.example.translationapproval.controller;

import com.example.translationapproval.model.Translation;
import com.example.translationapproval.service.TranslationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping
    public List<Translation> getAll() {
        return translationService.findAll();
    }

    @GetMapping("/{id}")
    public Translation getById(@PathVariable Long id) {
        return translationService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Translation> create(
            @RequestBody Translation translation) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(translationService.create(translation));
    }

    @PatchMapping("/{id}")
    public Translation update(
            @PathVariable Long id,
            @RequestBody Translation translation) {
        return translationService.update(id, translation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        translationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
