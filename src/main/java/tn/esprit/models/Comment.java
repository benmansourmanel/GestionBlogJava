package tn.esprit.models;

import java.time.LocalDate;
import java.util.Date;

public class Comment {
    private int id;
    private String content;
    private LocalDate createdAt;
    private int postId;
    private int userId;

    public Comment() {
    }

    public Comment(int id, String content, LocalDate createdAt, int postId, int userId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.postId = postId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Comment {" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}
