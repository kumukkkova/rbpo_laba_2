package org.example.model;

public class SourceSegment {

    private Long id;
    private Long projectId;
    private String sourceText;
    private Integer order;

    public SourceSegment() {
    }

    public SourceSegment(Long id, Long projectId,
                         String sourceText, Integer order) {
        this.id = id;
        this.projectId = projectId;
        this.sourceText = sourceText;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
