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
        AttributeContainer attributeContainer = new AttributeContainer(context, attrs);
        setup(attributeContainer);
    }

    private void setup(AttributeContainer container) {
        if (container.isEnableLottieAnimation()) {
            // inflate lottie animated reaction
            inflateWithLottie();
        } else {
            // inflate image reaction
            inflateWithoutLottie();
        }

        //create bg
        createSelectorBackground();

    }

    private void createSelectorBackground() {
        setBackground(getResources().getDrawable(R.drawable.selector_oval_background));
    }


    private void inflateWithoutLottie() {
        removeAllViews();

    }

    private void inflateWithLottie() {
        removeAllViews();
        for (Reaction reaction : Reaction.DEFAULT_LIST_LOTTIE) {
            ReactionSelectorComponent selectorComponent =
                    new ReactionSelectorComponent(getContext(), reaction);
            addView(selectorComponent);
        }
    }



    public ReactionSelectorListener getReactionSelectorListener() {
        return reactionSelectorListener;
    }

    public class ReactionSelectorListener implements GuesterListeners {
        @Override
        public void move(float oldX, float newX, float oldY, float newY) {

        }
    }


    protected class AttributeContainer {
        private boolean enableLottieAnimation;

        public AttributeContainer(final Context context,
                                  final AttributeSet attributeSet) {

        }

        public boolean isEnableLottieAnimation() {
            return enableLottieAnimation;
        }
    }
}

