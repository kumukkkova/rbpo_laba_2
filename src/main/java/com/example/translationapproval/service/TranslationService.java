
package com.example.translationapproval.service;

import com.example.translationapproval.exception.ResourceNotFoundException;
import com.example.translationapproval.model.Translation;
import com.example.translationapproval.model.SourceSegment;
import com.example.translationapproval.model.User;
import com.example.translationapproval.repository.TranslationRepository;
import com.example.translationapproval.repository.SourceSegmentRepository;
import com.example.translationapproval.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final SourceSegmentRepository segmentRepository;
    private final UserRepository userRepository;

    public TranslationService(
            TranslationRepository translationRepository,
            SourceSegmentRepository segmentRepository,
            UserRepository userRepository) {
        this.translationRepository = translationRepository;
        this.segmentRepository = segmentRepository;
        this.userRepository = userRepository;
    }

    public List<Translation> findAll() {
        return translationRepository.findAll();
    }

    public Translation findById(Long id) {
        return translationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Translation not found: " + id));
    }

    public Translation create(Translation translation) {
        if (translation.getSourceSegment() == null ||
                translation.getSourceSegment().getId() == null) {
            throw new IllegalArgumentException("Source segment ID is required");
        }

        if (translation.getAuthor() == null ||
                translation.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author ID is required");
        }

        SourceSegment segment = segmentRepository.findById(
                translation.getSourceSegment().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Source segment not found"));

        User author = userRepository.findById(
                translation.getAuthor().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Author not found"));

        translation.setSourceSegment(segment);
        translation.setAuthor(author);

        return translationRepository.save(translation);
    }

    public Translation update(Long id, Translation updatedTranslation) {
        Translation translation = findById(id);

        if (updatedTranslation.getSourceSegment() != null) {
            if (updatedTranslation.getSourceSegment().getId() == null) {
                throw new IllegalArgumentException("Source segment ID is required");
            }

            SourceSegment segment = segmentRepository.findById(
                    updatedTranslation.getSourceSegment().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Source segment not found"));

            translation.setSourceSegment(segment);
        }

        if (updatedTranslation.getAuthor() != null) {
            if (updatedTranslation.getAuthor().getId() == null) {
                throw new IllegalArgumentException("Author ID is required");
            }

            User author = userRepository.findById(
                    updatedTranslation.getAuthor().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Author not found"));

            translation.setAuthor(author);
        }

        if (updatedTranslation.getText() != null) {
            translation.setText(updatedTranslation.getText());
        }

        if (updatedTranslation.getStatus() != null) {
            translation.setStatus(updatedTranslation.getStatus());
        }

        return translationRepository.save(translation);
    }

    public void delete(Long id) {
        translationRepository.delete(findById(id));
    }
}
