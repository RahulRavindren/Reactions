package com.app.reactions_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class ReactionSelectorComponent extends LinearLayout {
    private Reaction reaction;

    public ReactionSelectorComponent(@NonNull Context context,
                                     @NonNull Reaction reaction) {
        this(context, null, reaction);
    }

    public ReactionSelectorComponent(@NonNull Context context,
                                     @Nullable AttributeSet attrs,
                                     @NonNull Reaction reaction) {
        this(context, attrs, 0, reaction);
    }

    public ReactionSelectorComponent(@NonNull Context context,
                                     @Nullable AttributeSet attrs,
                                     int defStyleAttr,
                                     @NonNull Reaction reaction) {
        super(context, attrs, defStyleAttr);
        this.reaction = reaction;
        setup();
    }


    private void setup() {
        setOrientation(LinearLayout.VERTICAL);
        removeAllViews();

        inflateTextView();
        if (reaction.isLottie()) {
            inflateLottieView();
        } else {
            inflateImageView();
        }

    }

    private void inflateImageView() {
        //todo
    }

    private void inflateLottieView() {
        LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
        lottieAnimationView.setAnimation("lottie/" + reaction.getGifPath());
        lottieAnimationView.loop(true);

        addView(lottieAnimationView);
    }

    private void inflateTextView() {
        TextView textView = new TextView(getContext());
        textView.setText(reaction.getTitle());
        LinearLayout.LayoutParams params =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;

        textView.setLayoutParams(params);
        textView.setBackground(getResources().getDrawable(R.drawable.selector_component_dark_background));
        addView(textView);
    }
}
