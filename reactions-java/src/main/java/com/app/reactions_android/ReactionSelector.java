package com.app.reactions_android;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.app.reactions_android.delegate.ReactionButtonDelegate;
import com.app.reactions_android.listeners.GuesterListeners;

import java.util.LinkedHashMap;
import java.util.Map;


public class ReactionSelector extends LinearLayout {
    private final ReactionButtonDelegate delegate;
    private ReactionSelectorListener reactionSelectorListener = new ReactionSelectorListener();
    private LinkedHashMap<View, Rect> viewRectMap = new LinkedHashMap<>();

    public ReactionSelector(Context context, ReactionButtonDelegate delegate) {
        this(context, null, delegate);
    }

    public ReactionSelector(Context context, @Nullable AttributeSet attrs, ReactionButtonDelegate delegate) {
        this(context, attrs, 0, delegate);
    }

    public ReactionSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr, ReactionButtonDelegate delegate) {
        super(context, attrs, defStyleAttr);
        this.delegate = delegate;
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
            selectorComponent.setTag(reaction);
            //add view to rect cache
            viewRectMap.put(selectorComponent, new Rect(selectorComponent.getTop(),
                    selectorComponent.getLeft(), selectorComponent.getRight(),
                    selectorComponent.getBottom()));
            addView(selectorComponent);
        }
    }


    public ReactionSelectorListener getReactionSelectorListener() {
        return reactionSelectorListener;
    }

    public class ReactionSelectorListener implements GuesterListeners {
        @Override
        public void move(float oldX, float newX, float oldY, float newY) {
            for (Map.Entry<View, Rect> entry : viewRectMap.entrySet()) {
                entry.getKey().getHitRect(entry.getValue());
                if (entry.getValue().contains((int) newX, (int) newY)) {
                    //typecast to reaction
                    delegate.hoveredReaction((Reaction) entry.getKey().getTag());
                }
            }
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

