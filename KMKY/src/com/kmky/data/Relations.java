package com.kmky.data;

import android.graphics.drawable.Drawable;

/**
 * This class is used to create relationship content between the user and any of his/her relations. For example this allows storing relationship data in lists.
 */
public class Relations
{

    public Drawable smsHeartYou;
    public Drawable callHeartYou;
    public Drawable smsHeartMe;
    public Drawable callHeartMe;
    public String name;

    public Relations()
    {
        super();
    }

    public Relations(Drawable smsHeartMe,Drawable callHeartMe, String name, Drawable smsHeartYou, Drawable callHeartYou)
    {
        super();
        this.smsHeartMe = smsHeartMe;
        this.callHeartMe = callHeartMe;
        this.smsHeartYou = smsHeartYou;
        this.callHeartYou = callHeartYou;
        this.name = name;
    }


}
