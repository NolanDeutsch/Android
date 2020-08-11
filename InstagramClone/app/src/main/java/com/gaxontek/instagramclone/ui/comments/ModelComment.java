package com.gaxontek.instagramclone.ui.comments;

public class ModelComment {
    public String id, image, name, comment, commentTime;
    int likes;

    public ModelComment() {

    }

    public ModelComment(String id, String image, String name, String comment, String commentTime, int likes) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.comment = comment;
        this.commentTime = commentTime;
        this.likes = likes;

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) { this.image = image; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() { return commentTime; }
    public void setCommentTime(String postTime) {
        this.commentTime = postTime;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
}
