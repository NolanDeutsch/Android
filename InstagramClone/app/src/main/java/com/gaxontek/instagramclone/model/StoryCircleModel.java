package com.gaxontek.instagramclone.model;

public class StoryCircleModel {
    public String mId, mName, mImage;

    public StoryCircleModel() {

    }

    public StoryCircleModel(String mId, String mImage, String mName) {
        this.mId = mId;
        this.mImage = mImage;
        this.mName = mName;
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

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
