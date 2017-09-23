package com.app.reactions_android;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReactionButton extends RelativeLayout {
    private ImageView iconImageView;
    private TextView titleLabel;
    
    public ReactionButton(Context context) {
        super(context);
    }

    public ReactionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReactionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setup() {

    }

}
