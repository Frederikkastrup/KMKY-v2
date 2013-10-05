package com.kmky.data;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kmky.R;

/**
 * Created by W520 on 05-10-13.
 */
public class Calculate {

    Context mContext;

    Calculate()
    {
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Drawable my_calls(int outgoing, int incoming, Context context)
    {
        Drawable drawable = null;

        int outgoing_treshold = (outgoing/(outgoing + incoming)) * 100;

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart30);
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart35);
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart40);
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart45);
        }

        else
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart50);
        }

        return  drawable;
    }

    public Drawable my_sms(int outgoing, int incoming, Context context)
    {
        // My treshold
        Drawable drawable = null;

        int outgoing_treshold = (outgoing/(outgoing + incoming)) * 100;

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart60);
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart70);
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart80);
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart90);
        }

        else
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart100);
        }

        return  drawable;

    }
    //
    public Drawable your_calls(int incoming, int outgoing, Context context)
    {
        Drawable drawable = null;

        int outgoing_treshold = (incoming/(outgoing + incoming)) * 100;

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart30);
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart35);
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart40);
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart45);
        }

        else
        {
            drawable = context.getResources().getDrawable(R.drawable.insideheart50);
        }

        return  drawable;
    }

    public Drawable your_sms(int incoming, int outgoing, Context context)
    {
        Drawable drawable = null;

        int outgoing_treshold = (incoming/(outgoing + incoming)) * 100;

        if ((outgoing_treshold > 1) && (outgoing_treshold <= 20))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart60);
        }

        else if ((outgoing_treshold > 20) && (outgoing_treshold <= 40))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart70);
        }

        else if ((outgoing_treshold > 40) && (outgoing_treshold <= 60))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart80);
        }

        else if ((outgoing_treshold > 60) && (outgoing_treshold <= 80))
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart90);
        }

        else
        {
            drawable = context.getResources().getDrawable(R.drawable.outsideheart100);
        }

        return  drawable;
    }

}

