package com.kmky.logic;

import android.graphics.drawable.Drawable;

import java.util.Date;

/**
 * Created by FrederikKastrup on 12/10/13.
 */
public class AnimationHearts {

    private Drawable mSmsHeartMe;
    private Drawable mCallHeartMe;
    private Drawable mSmsHeartYou;
    private Drawable mCallHeartYou;
    private Date mTimestamp;


    public AnimationHearts(Drawable mSmsHeartMe, Drawable mCallHeartMe, Drawable mSmsHeartYou, Drawable mCallHeartYou, Date mTimestamp) {
        this.mSmsHeartMe = mSmsHeartMe;
        this.mCallHeartMe = mCallHeartMe;
        this.mSmsHeartYou = mSmsHeartYou;
        this.mCallHeartYou = mCallHeartYou;
        this.mTimestamp = mTimestamp;
    }

    public Drawable getmSmsHeartMe() {
        return mSmsHeartMe;
    }

    public void setmSmsHeartMe(Drawable mSmsHeartMe) {
        this.mSmsHeartMe = mSmsHeartMe;
    }

    public Drawable getmCallHeartMe() {
        return mCallHeartMe;
    }

    public void setmCallHeartMe(Drawable mCallHeartMe) {
        this.mCallHeartMe = mCallHeartMe;
    }

    public Drawable getmSmsHeartYou() {
        return mSmsHeartYou;
    }

    public void setmSmsHeartYou(Drawable mSmsHeartYou) {
        this.mSmsHeartYou = mSmsHeartYou;
    }

    public Drawable getmCallHeartYou() {
        return mCallHeartYou;
    }

    public void setmCallHeartYou(Drawable mCallHeartYou) {
        this.mCallHeartYou = mCallHeartYou;
    }

    public Date getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }
}
