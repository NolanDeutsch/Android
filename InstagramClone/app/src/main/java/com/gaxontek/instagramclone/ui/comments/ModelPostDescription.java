package com.gaxontek.instagramclone.ui.comments;

public class ModelPostDescription {
    public String mId, mName, mImage, mDescription, mPostTime;

    public ModelPostDescription() {

    }

    public ModelPostDescription(String mId, String mImage, String mName, String mDescription, String mPostTime) {
        this.mId = mId;
        this.mImage = mImage;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mPostTime = mPostTime;

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

    public String getmDescription() {
        return mDescription;
    }
    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPostTime() { return mPostTime; }
    public void setmPostTime(String mPostTime) {
        this.mPostTime = mPostTime;
    }

}
