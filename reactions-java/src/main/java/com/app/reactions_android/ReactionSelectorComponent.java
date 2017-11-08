package com.app.reactions_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ReactionSelectorComponent extends FrameLayout {

    public ReactionSelectorComponent(@NonNull Context context) {
        this(context, null);
    }

    public ReactionSelectorComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReactionSelectorComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }


    private void setup() {

    }

    private void inflateImageView() {
    }

    private void inflateLottieView() {
    }

    private void inflateTextView() {

    }
}
