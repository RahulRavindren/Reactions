package com.app.reactions_android;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.app.reactions_android.listeners.GuesterListeners;


public class ReactionSelector extends LinearLayout {
    private ReactionSelectorListener reactionSelectorListener = new ReactionSelectorListener();


    public ReactionSelector(Context context) {
        this(context, null);
    }

    public ReactionSelector(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReactionSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReactionSelectorListener getReactionSelectorListener() {
        return reactionSelectorListener;
    }

    public class ReactionSelectorListener implements GuesterListeners {
        @Override
        public void move(float oldX, float newX, float oldY, float newY) {

        }
    }
}

