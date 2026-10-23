package org.example.repository;

import org.example.model.EditorialComment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EditorialCommentRepository {

    private final Map<Long, EditorialComment> comments =
            new ConcurrentHashMap<>();

    private final AtomicLong nextId = new AtomicLong(1);

    public List<EditorialComment> findAll() {
        return new ArrayList<>(comments.values());
    }

    public Optional<EditorialComment> findById(Long id) {
        return Optional.ofNullable(comments.get(id));
    }

    public EditorialComment save(EditorialComment comment) {
        if (comment.getId() == null) {
            comment.setId(nextId.getAndIncrement());
        }

        comments.put(comment.getId(), comment);
        return comment;
    }

    public boolean deleteById(Long id) {
        return comments.remove(id) != null;
    }
}
