
package org.example.service;

import org.example.model.TranslationProject;
import org.example.repository.TranslationProjectRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class TranslationProjectService {

    private final TranslationProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TranslationProjectService(
            TranslationProjectRepository projectRepository,
            UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // Получить все проекты
    public List<TranslationProject> findAll() {
        return projectRepository.findAll();
    }

    // Получить проект по ID
    public TranslationProject findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Проект не найден"));
    }

    // Создать проект
    public TranslationProject create(TranslationProject project) {
        checkOwner(project.getOwnerId());
        project.setId(null);
        return projectRepository.save(project);
    }

    // Частично изменить проект
    public TranslationProject update(
            Long id, Map<String, Object> changes) {

        TranslationProject project = findById(id);

        if (changes.containsKey("name")) {
            project.setName(requireString(changes, "name"));
        }

        if (changes.containsKey("targetLanguage")) {
            project.setTargetLanguage(
                    requireString(changes, "targetLanguage"));
        }

        if (changes.containsKey("ownerId")) {
            Object value = changes.get("ownerId");

            if (!(value instanceof Number)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "ownerId должен быть числом");
            }

            project.setOwnerId(((Number) value).longValue());
            checkOwner(project.getOwnerId());
        }

        if (changes.containsKey("status")) {
            project.setStatus(requireString(changes, "status"));
        }

        return projectRepository.save(project);
    }

    // Удалить проект
    public void delete(Long id) {
        findById(id);
        projectRepository.deleteById(id);
    }

    // Проверить существование владельца
    private void checkOwner(Long ownerId) {
        if (ownerId == null ||
                !userRepository.findById(ownerId).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Пользователь-владелец не найден");
        }
    }

    // Проверить тип строкового поля
    private String requireString(
            Map<String, Object> changes, String field) {

        Object value = changes.get(field);

        if (!(value instanceof String)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    field + " должно быть строкой");
        }

        return (String) value;
    }
}
