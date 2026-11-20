package org.example.repository;

import org.example.model.TranslationProject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TranslationProjectRepository {

    private final Map<Long, TranslationProject> projects =
            new ConcurrentHashMap<>();

    private final AtomicLong nextId = new AtomicLong(1);

    public List<TranslationProject> findAll() {
        return new ArrayList<>(projects.values());
    }

    public Optional<TranslationProject> findById(Long id) {
        return Optional.ofNullable(projects.get(id));
    }

    public TranslationProject save(TranslationProject project) {
        if (project.getId() == null) {
            project.setId(nextId.getAndIncrement());
        }

        projects.put(project.getId(), project);
        return project;
    }

    public boolean deleteById(Long id) {
        return projects.remove(id) != null;
    }
}
