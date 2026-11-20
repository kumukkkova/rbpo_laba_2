
package com.example.translationapproval.repository;

import com.example.translationapproval.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
}
