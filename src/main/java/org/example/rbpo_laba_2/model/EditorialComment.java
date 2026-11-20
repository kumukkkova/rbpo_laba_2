package org.example.model;

public class EditorialComment {

    private Long id;
    private Long translationId;
    private String text;
    private boolean resolved;

    public EditorialComment() {
    }

    public EditorialComment(Long id, Long translationId,
                            String text, boolean resolved) {
        this.id = id;
        this.translationId = translationId;
        this.text = text;
        this.resolved = resolved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTranslationId() {
        return translationId;
    }

    public void setTranslationId(Long translationId) {
        this.translationId = translationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
