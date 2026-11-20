
package com.example.translationapproval.controller;

import com.example.translationapproval.model.SourceSegment;
import com.example.translationapproval.service.SourceSegmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/source-segments")
public class SourceSegmentController {

    private final SourceSegmentService segmentService;

    public SourceSegmentController(SourceSegmentService segmentService) {
        this.segmentService = segmentService;
    }

    @GetMapping
    public List<SourceSegment> getAll() {
        return segmentService.findAll();
    }

    @GetMapping("/{id}")
    public SourceSegment getById(@PathVariable Long id) {
        return segmentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<SourceSegment> create(
            @RequestBody SourceSegment segment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(segmentService.create(segment));
    }

    @PatchMapping("/{id}")
    public SourceSegment update(
            @PathVariable Long id,
            @RequestBody SourceSegment segment) {
        return segmentService.update(id, segment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        segmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
