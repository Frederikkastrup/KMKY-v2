package com.kmky.data;

import android.graphics.drawable.Drawable;

/**
 * Rubbish :-)
 * @author peter
 *
 */
public class Relations
{

    public Drawable outsideheart1;
    public Drawable insideheart1;
    public Drawable outsideheart2;
    public Drawable insideheart2;
    public String name;

    public Relations()
    {
        super();
    }

    public Relations(Drawable outsideheart1,Drawable insideheart1, String name, Drawable outsideheart2, Drawable insideheart2)
    {
        super();
        this.outsideheart1 = outsideheart1;
        this.insideheart1 = insideheart1;
        this.outsideheart2 = outsideheart2;
        this.insideheart2 = insideheart2;

        this.name = name;
    }


}
