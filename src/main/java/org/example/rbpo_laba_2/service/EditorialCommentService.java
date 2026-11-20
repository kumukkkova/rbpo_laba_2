
package org.example.service;

import org.example.model.EditorialComment;
import org.example.repository.EditorialCommentRepository;
import org.example.repository.TranslationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

    // Получить все комментарии
    public List<EditorialComment> findAll() {
        return commentRepository.findAll();
    }

    // Получить комментарий по ID
    public EditorialComment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Комментарий не найден"));
    }

    // Создать комментарий
    public EditorialComment create(EditorialComment comment) {
        checkTranslation(comment.getTranslationId());
        comment.setId(null);
        return commentRepository.save(comment);
    }

    // Частично изменить комментарий
    public EditorialComment update(
            Long id, Map<String, Object> changes) {

        EditorialComment comment = findById(id);

        if (changes.containsKey("translationId")) {
            Object value = changes.get("translationId");

            if (!(value instanceof Number)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "translationId должен быть числом");
            }

            comment.setTranslationId(((Number) value).longValue());
            checkTranslation(comment.getTranslationId());
        }

        if (changes.containsKey("text")) {
            Object value = changes.get("text");

            if (!(value instanceof String)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "text должен быть строкой");
            }

            comment.setText((String) value);
        }

        if (changes.containsKey("resolved")) {
            Object value = changes.get("resolved");

            if (!(value instanceof Boolean)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "resolved должен быть true или false");
            }

            comment.setResolved((Boolean) value);
        }

        return commentRepository.save(comment);
    }

    // Удалить комментарий
    public void delete(Long id) {
        findById(id);
        commentRepository.deleteById(id);
    }

    private void checkTranslation(Long translationId) {
        if (translationId == null ||
                !translationRepository.findById(translationId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Перевод не найден");
        }
    }
}
