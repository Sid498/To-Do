package com.example.sid.to_do;

/**
 * Created by SID on 7/24/2017.
 */

public class Task_List {

    private int mId;
    private int mStatus;
    private String mTitle;
    private String mDescription;
    private String mTargetDate;

    public Task_List(int mId, String mTitle, String mDescription, String mTargetDate) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mTargetDate = mTargetDate;
        this.mStatus = 0;
    }

    public Task_List() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmTargetDate() {
        return mTargetDate;
    }

    public void setmTargetDate(String mTargetDate) {
        this.mTargetDate = mTargetDate;
    }


}
