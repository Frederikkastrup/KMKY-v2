package com.kmky.data;

import android.graphics.drawable.Drawable;

/**
 * This class is used to create relationship content between the user and any of his/her relations. For example this allows storing relationship data in lists.
 */
public class Relations {

    private Drawable smsHeartYou;
    private Drawable callHeartYou;
    private Drawable smsHeartMe;
    private Drawable callHeartMe;
    private String name;


    public Relations(Drawable smsHeartMe, Drawable callHeartMe, String name, Drawable smsHeartYou, Drawable callHeartYou)  {

        this.smsHeartMe = smsHeartMe;
        this.callHeartMe = callHeartMe;
        this.smsHeartYou = smsHeartYou;
        this.callHeartYou = callHeartYou;
        this.name = name;
    }

    public Drawable getSmsHeartYou() {
        return smsHeartYou;
    }

    public void setSmsHeartYou(Drawable smsHeartYou) {
        this.smsHeartYou = smsHeartYou;
    }

    public Drawable getCallHeartYou() {
        return callHeartYou;
    }

    public void setCallHeartYou(Drawable callHeartYou) {
        this.callHeartYou = callHeartYou;
    }

    public Drawable getSmsHeartMe() {
        return smsHeartMe;
    }

    public void setSmsHeartMe(Drawable smsHeartMe) {
        this.smsHeartMe = smsHeartMe;
    }

    public Drawable getCallHeartMe() {
        return callHeartMe;
    }

    public void setCallHeartMe(Drawable callHeartMe) {
        this.callHeartMe = callHeartMe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
