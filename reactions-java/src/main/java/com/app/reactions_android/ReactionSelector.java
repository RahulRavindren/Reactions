package com.app.reactions_android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.app.reactions_android.delegate.ReactionButtonDelegate;

import java.util.LinkedHashMap;


public class ReactionSelector extends LinearLayout {
    public static final String TAG_NAME = ReactionSelector.class.getSimpleName();
    private final ReactionButtonDelegate delegate;
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
        setPadding(15, 20, 15, 20);
        if (container.isEnableLottieAnimation()) {
            // inflate lottie animated reaction
            inflateWithLottie(container);
        } else {
            // inflate image reaction
            inflateWithoutLottie(container);
        }

        populateRectMap();
        //create bg
        createSelectorBackground();
    }

    private void createSelectorBackground() {
        setBackground(getResources().getDrawable(R.drawable.selector_oval_background));
    }

    private void populateRectMap() {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            Rect vr = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.getGlobalVisibleRect(vr);
            viewRectMap.put(v, vr);
        }
    }


    private void inflateWithoutLottie(AttributeContainer container) {
        removeAllViews();
        for (Reaction reaction : Reaction.DEFAULT_LIST) {
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

    private void inflateWithLottie(AttributeContainer container) {
        removeAllViews();
        for (Reaction reaction : Reaction.DEFAULT_LIST_LOTTIE) {
            ReactionSelectorComponent selectorComponent =
                    new ReactionSelectorComponent(getContext(), reaction);
            selectorComponent.setTag(reaction);

            addView(selectorComponent);
        }
    }



    protected class AttributeContainer {
        private boolean enableLottieAnimation;

        public AttributeContainer(final Context context,
                                  final AttributeSet attributeSet) {
            if (attributeSet != null) {
                TypedArray a = getContext().getTheme()
                        .obtainStyledAttributes(attributeSet, R.styleable.ReactionButton, 0, 0);
                try {
                    enableLottieAnimation = a.getBoolean(R.styleable.ReactionButton_RB_enable_reaction_animation, true);
                } finally {
                    a.recycle();
                }

            }
        }

        public boolean isEnableLottieAnimation() {
            return enableLottieAnimation;
        }
    }


}

