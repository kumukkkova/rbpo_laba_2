
package com.example.translationapproval.controller;

import com.example.translationapproval.model.TranslationProject;
import com.example.translationapproval.service.TranslationProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translation-projects")
public class TranslationProjectController {

    private final TranslationProjectService projectService;

    public TranslationProjectController(TranslationProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<TranslationProject> getAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public TranslationProject getById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public ResponseEntity<TranslationProject> create(
            @RequestBody TranslationProject project) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.create(project));
    }

    @PatchMapping("/{id}")
    public TranslationProject update(
            @PathVariable Long id,
            @RequestBody TranslationProject project) {
        return projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
