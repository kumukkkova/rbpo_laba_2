
package com.example.translationapproval.service;

import com.example.translationapproval.exception.ResourceNotFoundException;
import com.example.translationapproval.model.EditorialComment;
import com.example.translationapproval.model.Translation;
import com.example.translationapproval.repository.EditorialCommentRepository;
import com.example.translationapproval.repository.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorialCommentService {

    private final EditorialCommentRepository commentRepository;
    private final TranslationRepository translationRepository;

    public EditorialCommentService(
            EditorialCommentRepository commentRepository,
            TranslationRepository translationRepository) {
        this.commentRepository = commentRepository;
        this.translationRepository = translationRepository;
    }

    public List<EditorialComment> findAll() {
        return commentRepository.findAll();
    }

    public EditorialComment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found: " + id));
    }

    public EditorialComment create(EditorialComment comment) {
        if (comment.getTranslation() == null ||
                comment.getTranslation().getId() == null) {
            throw new IllegalArgumentException("Translation ID is required");
        }

        Translation translation = translationRepository.findById(
                comment.getTranslation().getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Translation not found"));

        comment.setTranslation(translation);

        return commentRepository.save(comment);
    }

    public EditorialComment update(Long id, EditorialComment updatedComment) {
        EditorialComment comment = findById(id);

        if (updatedComment.getTranslation() != null) {
            if (updatedComment.getTranslation().getId() == null) {
                throw new IllegalArgumentException("Translation ID is required");
            }

            Translation translation = translationRepository.findById(
                    updatedComment.getTranslation().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Translation not found"));

            comment.setTranslation(translation);
        }

        if (updatedComment.getCommentText() != null) {
            comment.setCommentText(updatedComment.getCommentText());
        }

        if (updatedComment.getResolved() != null) {
            comment.setResolved(updatedComment.getResolved());
        }

        return commentRepository.save(comment);
    }

    public void delete(Long id) {
        commentRepository.delete(findById(id));
    }
}
