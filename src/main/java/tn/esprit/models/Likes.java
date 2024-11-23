package tn.esprit.models;

import java.time.LocalDate;

public class Likes {
    private int id;
    private int postId;
    private int userId;
    private boolean isLiked;


    public Likes() {
    }

    public Likes(int id, int postId, int userId, boolean isLiked) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.isLiked = isLiked;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }



    @Override
    public String toString() {
        return "Likes {" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", isLiked=" + isLiked +

                '}';
    }
}
