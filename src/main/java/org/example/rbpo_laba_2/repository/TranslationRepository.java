package org.example.repository;

import org.example.model.Translation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TranslationRepository {

    private final Map<Long, Translation> translations =
            new ConcurrentHashMap<>();

    private final AtomicLong nextId = new AtomicLong(1);

    public List<Translation> findAll() {
        return new ArrayList<>(translations.values());
    }

    public Optional<Translation> findById(Long id) {
        return Optional.ofNullable(translations.get(id));
    }

    public Translation save(Translation translation) {
        if (translation.getId() == null) {
            translation.setId(nextId.getAndIncrement());
        }

        translations.put(translation.getId(), translation);
        return translation;
    }

    public boolean deleteById(Long id) {
        return translations.remove(id) != null;
    }
}
