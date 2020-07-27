package com.gaxontek.instagramclone.ui.comments;

public class ModelComment {
    public String mId, mName, mImage, mComment, mPostTime, mLikes;

    public ModelComment() {

    }

    public ModelComment(String mId, String mImage, String mName, String mComment, String mPostTime, String mLikes) {
        this.mId = mId;
        this.mImage = mImage;
        this.mName = mName;
        this.mComment = mComment;
        this.mPostTime = mPostTime;
        this.mLikes = mLikes;

    }

    public String getmId() {
        return mId;
    }
    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmImage() {
        return mImage;
    }
    public void setmImage(String mImage) { this.mImage = mImage; }

    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmComment() {
        return mComment;
    }
    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmPostTime() { return mPostTime; }
    public void setmPostTime(String mPostTime) {
        this.mPostTime = mPostTime;
    }

    public String getmLikes() {
        return mLikes;
    }
    public void setmLikes(String mLikes) {
        this.mLikes = mLikes;
    }
}
