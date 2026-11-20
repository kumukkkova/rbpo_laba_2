
package com.example.translationapproval.repository;

import com.example.translationapproval.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
