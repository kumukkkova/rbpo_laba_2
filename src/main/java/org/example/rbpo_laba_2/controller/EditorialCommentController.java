
package org.example.controller;

import org.example.model.EditorialComment;
import org.example.service.EditorialCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/editorial-comments")
public class EditorialCommentController {

    private final EditorialCommentService commentService;

    public EditorialCommentController(
            EditorialCommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/editorial-comments
    @GetMapping
    public List<EditorialComment> findAll() {
        return commentService.findAll();
    }

    // GET /api/editorial-comments/{id}
    @GetMapping("/{id}")
    public EditorialComment findById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    // POST /api/editorial-comments
    @PostMapping
    public ResponseEntity<EditorialComment> create(
            @RequestBody EditorialComment comment) {
        EditorialComment created = commentService.create(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/editorial-comments/{id}
    @PatchMapping("/{id}")
    public EditorialComment update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> changes) {
        return commentService.update(id, changes);
    }

    // DELETE /api/editorial-comments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
