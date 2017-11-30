package com.app.reactions_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
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
        setPadding(10, 0, 0, 0);
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
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(reaction.getIcon());
        LayoutParams params = new LayoutParams(ViewUtils.dpToPx(getContext(), 40), ViewUtils.dpToPx(getContext(), 40));
        imageView.setLayoutParams(params);

        addView(imageView);
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
        textView.setVisibility(GONE);
    }

}
