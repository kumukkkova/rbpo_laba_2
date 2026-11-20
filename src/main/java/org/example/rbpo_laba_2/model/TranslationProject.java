package org.example.model;

public class TranslationProject {

    private Long id;
    private String name;
    private String targetLanguage;
    private Long ownerId;
    private String status;

    public TranslationProject() {
    }

    public TranslationProject(Long id, String name,
                              String targetLanguage,
                              Long ownerId, String status) {
        this.id = id;
        this.name = name;
        this.targetLanguage = targetLanguage;
        this.ownerId = ownerId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
