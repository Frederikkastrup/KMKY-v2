package com.kmky.data;

import android.graphics.drawable.Drawable;

/**
 * This class is used to create relationship content between the user and any of his/her relations. For example this allows storing relationship data in lists.
 */
public class Relations
{

    public Drawable mysmsheart;
    public Drawable mycallheart;
    public Drawable yoursmsheart;
    public Drawable yourcallheart;
    public String name;

    public Relations()
    {
        super();
    }

    public Relations(Drawable mysmsheart,Drawable mycallheart, String name, Drawable yoursmsheart, Drawable yourcallheart)
    {
        super();
        this.mysmsheart = mysmsheart;
        this.mycallheart = mycallheart;
        this.yoursmsheart = yoursmsheart;
        this.yourcallheart = yourcallheart;

        this.name = name;
    }


}
