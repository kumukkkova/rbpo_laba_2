
package com.example.translationapproval.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "source_segments")
@Getter
@Setter
public class SourceSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private TranslationProject project;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sourceText;

    @Column(nullable = false)
    private Integer position;
}
