
package com.example.translationapproval.service;

import com.example.translationapproval.exception.ResourceNotFoundException;
import com.example.translationapproval.model.TranslationProject;
import com.example.translationapproval.model.User;
import com.example.translationapproval.repository.TranslationProjectRepository;
import com.example.translationapproval.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TranslationProject> findAll() {
        return projectRepository.findAll();
    }

    public TranslationProject findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found: " + id));
    }

    public TranslationProject create(TranslationProject project) {
        if (project.getOwner() == null ||
                project.getOwner().getId() == null) {
            throw new IllegalArgumentException("Owner ID is required");
        }

        User owner = userRepository.findById(project.getOwner().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        project.setOwner(owner);

        return projectRepository.save(project);
    }

    public TranslationProject update(Long id, TranslationProject updatedProject) {
        TranslationProject project = findById(id);

        if (updatedProject.getName() != null) {
            project.setName(updatedProject.getName());
        }

        if (updatedProject.getTargetLanguage() != null) {
            project.setTargetLanguage(updatedProject.getTargetLanguage());
        }

        if (updatedProject.getOwner() != null) {
            if (updatedProject.getOwner().getId() == null) {
                throw new IllegalArgumentException("Owner ID is required");
            }

            User owner = userRepository.findById(
                    updatedProject.getOwner().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

            project.setOwner(owner);
        }

        if (updatedProject.getStatus() != null) {
            project.setStatus(updatedProject.getStatus());
        }

        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.delete(findById(id));
    }
}
