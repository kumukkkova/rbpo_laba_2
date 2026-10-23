package org.example.service;

import org.example.model.Translation;
import org.example.repository.SourceSegmentRepository;
import org.example.repository.TranslationRepository;
import org.example.repository.UserRepository;
import org.example.repository.EditorialCommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final SourceSegmentRepository segmentRepository;
    private final UserRepository userRepository;
    private final EditorialCommentRepository commentRepository;

    public TranslationService(
            TranslationRepository translationRepository,
            SourceSegmentRepository segmentRepository,
            UserRepository userRepository,
            EditorialCommentRepository commentRepository) {
        this.translationRepository = translationRepository;
        this.segmentRepository = segmentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    // Получить все переводы
    public List<Translation> findAll() {
        return translationRepository.findAll();
    }

    // Получить перевод по ID
    public Translation findById(Long id) {
        return translationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Перевод не найден"));
    }

    // Создать перевод
    public Translation create(Translation translation) {
        checkSegment(translation.getSegmentId());
        checkAuthor(translation.getAuthorId());
        translation.setId(null);
        return translationRepository.save(translation);
    }

    // Частично изменить перевод
    public Translation update(Long id, Map<String, Object> changes) {
        Translation translation = findById(id);

        if (changes.containsKey("segmentId")) {
            Object value = changes.get("segmentId");

            if (!(value instanceof Number)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "segmentId должен быть числом");
            }

            Long segmentId = ((Number) value).longValue();
            checkSegment(segmentId);
            translation.setSegmentId(segmentId);
        }

        if (changes.containsKey("authorId")) {
            Object value = changes.get("authorId");

            if (!(value instanceof Number)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "authorId должен быть числом");
            }

            Long authorId = ((Number) value).longValue();
            checkAuthor(authorId);
            translation.setAuthorId(authorId);
        }

        if (changes.containsKey("text")) {
            Object value = changes.get("text");

            if (!(value instanceof String)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "text должен быть строкой");
            }

            translation.setText((String) value);
        }

        if (changes.containsKey("status")) {
            Object value = changes.get("status");

            if (!(value instanceof String)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "status должен быть строкой");
            }

            translation.setStatus((String) value);
        }

        return translationRepository.save(translation);
    }

    // Удалить перевод и связанные с ним комментарии
    public void delete(Long id) {
        findById(id);

        commentRepository.findAll().stream()
                .filter(comment -> comment.getTranslationId() != null
                        && comment.getTranslationId().equals(id))
                .map(comment -> comment.getId())
                .toList()
                .forEach(commentRepository::deleteById);

        translationRepository.deleteById(id);
    }

    private void checkSegment(Long segmentId) {
        if (segmentId == null
                || segmentRepository.findById(segmentId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Исходный сегмент не найден");
        }
    }

    private void checkAuthor(Long authorId) {
        if (authorId == null
                || userRepository.findById(authorId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Автор не найден");
        }
    }
}
