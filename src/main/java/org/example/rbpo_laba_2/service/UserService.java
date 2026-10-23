
package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.TranslationProjectRepository;
import org.example.repository.TranslationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TranslationProjectRepository projectRepository;
    private final TranslationRepository translationRepository;

    public UserService(
            UserRepository userRepository,
            TranslationProjectRepository projectRepository,
            TranslationRepository translationRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.translationRepository = translationRepository;
    }

    // Получить всех пользователей
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Получить пользователя по ID
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден"));
    }

    // Создать пользователя
    public User create(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    // Частично изменить пользователя
    public User update(Long id, Map<String, Object> changes) {
        User user = findById(id);

        if (changes.containsKey("name")) {
            Object name = changes.get("name");
            if (!(name instanceof String)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Имя должно быть строкой");
            }
            user.setName((String) name);
        }

        if (changes.containsKey("email")) {
            Object email = changes.get("email");
            if (!(email instanceof String)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Email должен быть строкой");
            }
            user.setEmail((String) email);
        }

        return userRepository.save(user);
    }

    // Удалить пользователя
    public void delete(Long id) {
        findById(id);

        boolean ownsProject = projectRepository.findAll().stream()
                .anyMatch(project -> project.getOwnerId() != null
                        && project.getOwnerId().equals(id));

        boolean authoredTranslation = translationRepository.findAll().stream()
                .anyMatch(translation -> translation.getAuthorId() != null
                        && translation.getAuthorId().equals(id));

        if (ownsProject || authoredTranslation) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Нельзя удалить пользователя: он связан с проектом или переводом"
            );
        }

        userRepository.deleteById(id);
    }
}
