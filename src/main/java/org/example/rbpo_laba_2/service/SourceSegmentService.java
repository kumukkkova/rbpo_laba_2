
package org.example.service;

import org.example.model.SourceSegment;
import org.example.repository.SourceSegmentRepository;
import org.example.repository.TranslationProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

    // Получить все сегменты
    public List<SourceSegment> findAll() {
        return segmentRepository.findAll();
    }

    // Получить сегмент по ID
    public SourceSegment findById(Long id) {
        return segmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Сегмент не найден"));
    }

    // Создать сегмент
    public SourceSegment create(SourceSegment segment) {
        checkProject(segment.getProjectId());
        segment.setId(null);
        return segmentRepository.save(segment);
    }

    // Частично изменить сегмент
    public SourceSegment update(
            Long id, Map<String, Object> changes) {

        SourceSegment segment = findById(id);

        if (changes.containsKey("projectId")) {
            Object value = changes.get("projectId");

            if (!(value instanceof Number)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "projectId должен быть числом");
            }

            segment.setProjectId(((Number) value).longValue());
            checkProject(segment.getProjectId());
        }

        if (changes.containsKey("sourceText")) {
            Object value = changes.get("sourceText");

            if (!(value instanceof String)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "sourceText должен быть строкой");
            }

            segment.setSourceText((String) value);
        }

        if (changes.containsKey("order")) {
            Object value = changes.get("order");

            if (!(value instanceof Number)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "order должен быть числом");
            }

            segment.setOrder(((Number) value).intValue());
        }

        return segmentRepository.save(segment);
    }

    // Удалить сегмент
    public void delete(Long id) {
        findById(id);
        segmentRepository.deleteById(id);
    }

    // Проверить существование проекта
    private void checkProject(Long projectId) {
        if (projectId == null ||
                !projectRepository.findById(projectId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Проект не найден");
        }
    }
}
