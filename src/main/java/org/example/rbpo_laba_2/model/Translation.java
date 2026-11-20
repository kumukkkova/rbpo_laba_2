package org.example.model;

public class Translation {

    private Long id;
    private Long segmentId;
    private Long authorId;
    private String text;
    private String status;

    public Translation() {
    }

    public Translation(Long id, Long segmentId, Long authorId,
                       String text, String status) {
        this.id = id;
        this.segmentId = segmentId;
        this.authorId = authorId;
        this.text = text;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
