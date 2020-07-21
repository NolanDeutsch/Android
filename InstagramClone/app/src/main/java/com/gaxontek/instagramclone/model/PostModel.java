package com.gaxontek.instagramclone.model;

public class PostModel {
        public String mId, mPostUsername, mPostUserImage, mPostImage, mPostLikes, mPostDescription, mPostTime, mUserImage;

        public PostModel() {
        }

        public PostModel(String mId, String mPostUsername, String mPostUserImage, String mPostImage,
                         String mPostLikes, String mPostDescription, String mPostTime, String mUserImage) {
            this.mId = mId;
            this.mPostUsername = mPostUsername;
            this.mPostUserImage = mPostUserImage;
            this.mPostImage = mPostImage;
            this.mPostLikes = mPostLikes;
            this.mPostDescription = mPostDescription;
            this.mPostTime = mPostTime;
            this.mUserImage = mUserImage;
        }

        public String getmId() {
            return mId;
        }

        public void setmId(String mId) {
            this.mId = mId;
        }

        public String getmPostUsername() {
            return mPostUsername;
        }

        public void setmPostUsername(String mPostUsername) {
            this.mPostUsername = mPostUsername;
        }
        public String getmPostUserImage() {
            return mPostUserImage;
        }

        public void setmPostUserImage(String mPostUserImage) {
            this.mPostUserImage = mPostUserImage;
        }
        public String getmPostImage() {
            return mPostImage;
        }

        public void setmPostImage(String mPostImage) {
            this.mPostImage = mPostImage;
        }
        public String getmPostLikes() {
            return mPostLikes;
        }

        public void setmPostLikes(String mPostLikes) {
            this.mPostLikes = mPostLikes;
        }
        public String getmPostDescription() {
            return mPostDescription;
        }

        public void setmPostDescription(String mPostDescription) {
            this.mPostDescription = mPostDescription;
        }
        public String getmPostTime() {
            return mPostTime;
        }

        public void setmPostTime(String mPostTime) {
            this.mPostTime = mPostTime;
        }
        public String getmUserImage() {
            return mUserImage;
        }

        public void setmUserImage(String mUserImage) {
            this.mUserImage = mUserImage;
        }
}
