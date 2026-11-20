
package com.example.translationapproval.service;

import com.example.translationapproval.exception.ResourceNotFoundException;
import com.example.translationapproval.model.SourceSegment;
import com.example.translationapproval.model.TranslationProject;
import com.example.translationapproval.repository.SourceSegmentRepository;
import com.example.translationapproval.repository.TranslationProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceSegmentService {

    private final SourceSegmentRepository segmentRepository;
    private final TranslationProjectRepository projectRepository;

    public SourceSegmentService(
            SourceSegmentRepository segmentRepository,
            TranslationProjectRepository projectRepository) {
        this.segmentRepository = segmentRepository;
        this.projectRepository = projectRepository;
    }

    public List<SourceSegment> findAll() {
        return segmentRepository.findAll();
    }

    public SourceSegment findById(Long id) {
        return segmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Segment not found: " + id));
    }

    public SourceSegment create(SourceSegment segment) {
        if (segment.getProject() == null ||
                segment.getProject().getId() == null) {
            throw new IllegalArgumentException("Project ID is required");
        }

        TranslationProject project = projectRepository.findById(
                segment.getProject().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Project not found"));

        segment.setProject(project);

        return segmentRepository.save(segment);
    }

    public SourceSegment update(Long id, SourceSegment updatedSegment) {
        SourceSegment segment = findById(id);

        if (updatedSegment.getProject() != null) {
            if (updatedSegment.getProject().getId() == null) {
                throw new IllegalArgumentException("Project ID is required");
            }

            TranslationProject project = projectRepository.findById(
                    updatedSegment.getProject().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Project not found"));

            segment.setProject(project);
        }

        if (updatedSegment.getSourceText() != null) {
            segment.setSourceText(updatedSegment.getSourceText());
        }

        if (updatedSegment.getPosition() != null) {
            segment.setPosition(updatedSegment.getPosition());
        }

        return segmentRepository.save(segment);
    }

    public void delete(Long id) {
        segmentRepository.delete(findById(id));
    }
}
