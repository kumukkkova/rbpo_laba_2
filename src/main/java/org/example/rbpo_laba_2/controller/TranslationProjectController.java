
package org.example.controller;

import org.example.model.TranslationProject;
import org.example.service.TranslationProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translation-projects")
public class TranslationProjectController {

    private final TranslationProjectService projectService;

    public TranslationProjectController(
            TranslationProjectService projectService) {
        this.projectService = projectService;
    }

    // GET /api/translation-projects
    @GetMapping
    public List<TranslationProject> findAll() {
        return projectService.findAll();
    }

    // GET /api/translation-projects/{id}
    @GetMapping("/{id}")
    public TranslationProject findById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // POST /api/translation-projects
    @PostMapping
    public ResponseEntity<TranslationProject> create(
            @RequestBody TranslationProject project) {
        TranslationProject created = projectService.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/translation-projects/{id}
    @PatchMapping("/{id}")
    public TranslationProject update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> changes) {
        return projectService.update(id, changes);
    }

    // DELETE /api/translation-projects/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
