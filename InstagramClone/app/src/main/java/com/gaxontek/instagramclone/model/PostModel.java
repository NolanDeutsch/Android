package com.gaxontek.instagramclone.model;

import java.util.ArrayList;

public class PostModel {
    public String id, postUsername, postUserImage, postImage, postDescription, postTime;
    public int postLikes;
    public ArrayList<String> userLiked = new ArrayList<String>();

    public PostModel() { }

        public PostModel(String id, String postUsername, String postUserImage, String postImage,
                         int postLikes, ArrayList<String> userLiked, String postDescription, String postTime) {
            this.id = id;
            this.postUsername = postUsername;
            this.postUserImage = postUserImage;
            this.postImage = postImage;
            this.postLikes = postLikes;
            this.userLiked = userLiked;
            this.postDescription = postDescription;
            this.postTime = postTime;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getPostUsername() {
            return postUsername;
        }
        public void setPostUsername(String postUsername) {
            this.postUsername = postUsername;
        }

        public String getPostUserImage() {
            return postUserImage;
        }
        public void setPostUserImage(String postUserImage) { this.postUserImage = postUserImage; }

        public String getPostImage() {
            return postImage;
        }
        public void setPostImage(String postImage) {
            this.postImage = postImage;
        }

        public int getPostLikes() { return postLikes; }
        public void setPostLikes(int postLikes) {
            this.postLikes = postLikes;
        }

        public ArrayList<String> getUserLiked() { return  userLiked; }
        public void setUserLiked(ArrayList<String> userLiked) { this.userLiked = userLiked; }

        public String getPostDescription() {
            return postDescription;
        }
        public void setPostDescription(String postDescription) { this.postDescription = postDescription; }

        public String getPostTime() {
            return postTime;
        }
        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }
}
