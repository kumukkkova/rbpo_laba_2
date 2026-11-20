
package com.example.translationapproval.controller;

import com.example.translationapproval.model.EditorialComment;
import com.example.translationapproval.service.EditorialCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editorial-comments")
public class EditorialCommentController {

    private final EditorialCommentService commentService;

    public EditorialCommentController(EditorialCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<EditorialComment> getAll() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public EditorialComment getById(@PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<EditorialComment> create(
            @RequestBody EditorialComment comment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.create(comment));
    }

    @PatchMapping("/{id}")
    public EditorialComment update(
            @PathVariable Long id,
            @RequestBody EditorialComment comment) {
        return commentService.update(id, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
