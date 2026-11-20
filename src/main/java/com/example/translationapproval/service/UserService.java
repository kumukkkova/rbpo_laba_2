
package com.example.translationapproval.service;

import com.example.translationapproval.exception.ResourceNotFoundException;
import com.example.translationapproval.model.User;
import com.example.translationapproval.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + id));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {
        User user = findById(id);

        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findById(id));
    }
}
