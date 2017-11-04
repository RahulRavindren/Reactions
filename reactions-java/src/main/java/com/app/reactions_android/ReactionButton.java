package com.app.reactions_android;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class ReactionButton extends RelativeLayout {
    private ImageView iconImageView;
    private TextView titleLabel;
    
    public ReactionButton(Context context) {
        this(context, null);
    }

    public ReactionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReactionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        GestureRecogniser recogniser = new GestureRecogniser();
        setOnClickListener(recogniser);
        setOnLongClickListener(recogniser);
    }


    private void postInit() {

    }

    private class GestureRecogniser implements View.OnClickListener, View.OnLongClickListener {

        @Override
        public void onClick(View v) {
            if (v.equals(this)) {

            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.equals(this)) {

                return true;
            }
            return false;
        }
    }



}
