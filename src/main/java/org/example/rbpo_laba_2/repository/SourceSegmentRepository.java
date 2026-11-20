
package org.example.repository;

import org.example.model.SourceSegment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SourceSegmentRepository {

    private final Map<Long, SourceSegment> segments =
            new ConcurrentHashMap<>();

    private final AtomicLong nextId = new AtomicLong(1);

    public List<SourceSegment> findAll() {
        return new ArrayList<>(segments.values());
    }

    public Optional<SourceSegment> findById(Long id) {
        return Optional.ofNullable(segments.get(id));
    }

    public SourceSegment save(SourceSegment segment) {
        if (segment.getId() == null) {
            segment.setId(nextId.getAndIncrement());
        }

        segments.put(segment.getId(), segment);
        return segment;
    }

    public boolean deleteById(Long id) {
        return segments.remove(id) != null;
    }
}
