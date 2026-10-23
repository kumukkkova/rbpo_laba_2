package org.example.repository;

import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId.getAndIncrement());
        }

        users.put(user.getId(), user);
        return user;
    }

    public boolean deleteById(Long id) {
        return users.remove(id) != null;
    }
}
