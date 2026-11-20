
package com.example.translationapproval.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "editorial_comments")
@Getter
@Setter
public class EditorialComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "translation_id", nullable = false)
    private Translation translation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String commentText;

    @Column(nullable = false)
    private Boolean resolved = false;
}
