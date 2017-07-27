package com.chy.qrcode.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chy.qrcode.R;


/**
 * Created by wang on 2017/5/11.
 */

public class MyToast {
    private Toast mToast;
    private AnimationDrawable animationDrawable;
    private MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.eplay_toast, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_gif);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.FILL, 0, 0);
        mToast.setView(v);
    }

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }

    public void toastCancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
