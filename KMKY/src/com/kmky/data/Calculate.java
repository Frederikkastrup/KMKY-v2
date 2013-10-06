package com.kmky.data;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kmky.R;

/**
 * Created by W520 on 05-10-13..
 */
public class Calculate {

    private Context mContext;

    Calculate(Context context)
    {
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Drawable my_calls(int outgoing, int incoming, Context context)
    {
        int outgoing_treshold = 0;
        Drawable drawable = null;
        try{
        outgoing_treshold = (outgoing/(outgoing + incoming)) * 100;
        }
        catch (NullPointerException e)
        {
            outgoing_treshold = 0;
        }


        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart30);
            return  drawable;
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart35);
            return  drawable;
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart40);
            return  drawable;
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart45);
            return  drawable;
        }

        else if ((outgoing_treshold > 80) && (outgoing_treshold <= 100))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart50);
            return  drawable;
        }

        else { drawable = context.getResources().getDrawable(R.drawable.blank);
            return drawable;}

    }

    public Drawable my_sms(int outgoing, int incoming, Context context)
    {
        // My treshold
        int outgoing_treshold = 0;
        Drawable drawable = null;

        try{
            outgoing_treshold = (outgoing/(outgoing + incoming)) * 100;
        }
        catch (NullPointerException e)
        {
            outgoing_treshold = 0;
        }

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart60);
            return  drawable;
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart70);
            return  drawable;
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart80);
            return  drawable;
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart90);
            return  drawable;
        }

        else if ((outgoing_treshold > 80) && (outgoing_treshold <= 100))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart100);
            return  drawable;
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.blank);
            return drawable;
        }
    }



    //
    public Drawable your_calls(int incoming, int outgoing, Context context)
    {
        int outgoing_treshold = 0;
        Drawable drawable = null;

        try{
            outgoing_treshold = (incoming/(outgoing + incoming)) * 100;
        }
        catch (NullPointerException e)
        {
            outgoing_treshold = 0;
        }

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart30);
            return  drawable;
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart35);
            return  drawable;
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart40);
            return  drawable;
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart45);
            return  drawable;
        }

        else if ((outgoing_treshold > 80) && (outgoing_treshold <= 100))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart50);
            return  drawable;
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.blank);
            return drawable;
        }

    }

    public Drawable your_sms(int incoming, int outgoing, Context context)
    {
        int outgoing_treshold = 0;
        Drawable drawable = null;

        try{
            outgoing_treshold = (incoming/(outgoing + incoming)) * 100;
        }
        catch (NullPointerException e)
        {
            outgoing_treshold = 0;
        }

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart60);
            return drawable;
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart70);
            return drawable;
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart80);
            return drawable;
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart90);
            return drawable;
        }

        else if ((outgoing_treshold > 80) && (outgoing_treshold <= 100))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart90);
            return drawable;
        }

        else {
            drawable = context.getResources().getDrawable(R.drawable.blank);
            return drawable;
        }

    }

}

