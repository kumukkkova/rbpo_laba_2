
package org.example.controller;

import org.example.model.Translation;
import org.example.service.TranslationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    // GET /api/translations
    @GetMapping
    public List<Translation> findAll() {
        return translationService.findAll();
    }

    // GET /api/translations/{id}
    @GetMapping("/{id}")
    public Translation findById(@PathVariable Long id) {
        return translationService.findById(id);
    }

    // POST /api/translations
    @PostMapping
    public ResponseEntity<Translation> create(
            @RequestBody Translation translation) {
        Translation created = translationService.create(translation);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/translations/{id}
    @PatchMapping("/{id}")
    public Translation update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> changes) {
        return translationService.update(id, changes);
    }

    // DELETE /api/translations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        translationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
