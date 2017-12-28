package com.rahbod.visit365.Font;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonFont extends Button {
    public ButtonFont(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans.ttf");
        this.setTypeface(face);
    }

    public ButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans.ttf");
        this.setTypeface(face);
    }

    public ButtonFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans.ttf");
        this.setTypeface(face);
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }
}
