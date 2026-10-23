
package org.example.controller;

import org.example.model.SourceSegment;
import org.example.service.SourceSegmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/source-segments")
public class SourceSegmentController {

    private final SourceSegmentService segmentService;

    public SourceSegmentController(SourceSegmentService segmentService) {
        this.segmentService = segmentService;
    }

    // GET /api/source-segments
    @GetMapping
    public List<SourceSegment> findAll() {
        return segmentService.findAll();
    }

    // GET /api/source-segments/{id}
    @GetMapping("/{id}")
    public SourceSegment findById(@PathVariable Long id) {
        return segmentService.findById(id);
    }

    // POST /api/source-segments
    @PostMapping
    public ResponseEntity<SourceSegment> create(
            @RequestBody SourceSegment segment) {
        SourceSegment created = segmentService.create(segment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/source-segments/{id}
    @PatchMapping("/{id}")
    public SourceSegment update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> changes) {
        return segmentService.update(id, changes);
    }

    // DELETE /api/source-segments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        segmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
