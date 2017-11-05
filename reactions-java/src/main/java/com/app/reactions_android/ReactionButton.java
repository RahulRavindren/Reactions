package com.app.reactions_android;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.reactions_android.enums.TEXT_RELATIVE_ALIGNMENT;

public final class ReactionButton extends RelativeLayout {
    //launch reaction selector
    ReactionSelector reactionSelector = new ReactionSelector(getContext());
    private ImageView iconImageView;
    private TextView titleLabel;
    private int fontIconSpacing;
    private int fontSize;
    @ColorRes
    private int unselectedColorRes;
    private String fontRelativeAlignment;
    private boolean isSelected;



    public ReactionButton(Context context) {
        this(context, null);
    }

    public ReactionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReactionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
        postInit(attrs);
    }

    private void setup() {
        GestureRecogniser recogniser = new GestureRecogniser();
        setOnTouchListener(recogniser);

    }


    private void postInit(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attributeSet, R.styleable.ReactionButton, 0, 0);
            try {
                fontIconSpacing = a.getInt(R.styleable.ReactionButton_RB_text_icon_spacing,
                        (int) getContext().getResources().getDimension(R.dimen.font_icon_spacing));
                fontSize = a.getInt(R.styleable.ReactionButton_RB_font_size, 20);
                unselectedColorRes = a.getResourceId(R.styleable.ReactionButton_RB_unselected_tint_color, 0);
                fontRelativeAlignment = a.getString(R.styleable.ReactionButton_RB_text_icon_alignment);
                inflateButton();
                inflateText();
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private void inflateButton() {
        ImageButton button = new ImageButton(getContext());
        //defualt pick
        Reaction defaultReaction = Reaction.LIKE;
        button.setBackground(null);
        button.setImageResource(isSelected ? defaultReaction.getIcon() : defaultReaction.getAlternativeIcon());
        button.setForeground(getResources().getDrawable(unselectedColorRes));
        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TEXT_RELATIVE_ALIGNMENT align = TEXT_RELATIVE_ALIGNMENT.getAlignment(fontRelativeAlignment);
        switch (align) {
            case RIGHT:
                params.rightMargin = fontIconSpacing;
                break;
            case LEFT:
                params.leftMargin = fontIconSpacing;
        }
        button.setLayoutParams(params);
        addView(button);
    }

    private void inflateText() {
        TextView textView = new TextView(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TEXT_RELATIVE_ALIGNMENT align = TEXT_RELATIVE_ALIGNMENT.getAlignment(fontRelativeAlignment);
        if (align == null) {
            align = TEXT_RELATIVE_ALIGNMENT.RIGHT;
            //log
        }

        switch (align) {
            case LEFT:
                params.addRule(RelativeLayout.LEFT_OF, getChildAt(0).getId());
                break;
            case RIGHT:
                params.addRule(RelativeLayout.RIGHT_OF, getChildAt(0).getId());
                break;
        }
        textView.setLayoutParams(params);
        textView.setTextColor(isSelected ? getResources().getColor(android.R.color.darker_gray) : -1);
        addView(textView);
    }

    class GestureRecogniser implements View.OnTouchListener {
        private final float SCROLL_THRESHOLD = 10;
        private float downX;
        private float downY;
        private boolean isOnClick;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    isOnClick = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
                case MotionEvent.ACTION_UP:
                    if (isOnClick && view.equals(this)) {
                        //onclick action
                        ObjectAnimator.ofFloat(view, "scaleX", 1.8f, 1.8f)
                                .setDuration(300).start();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isOnClick && (Math.abs(downX - motionEvent.getX()) > SCROLL_THRESHOLD) ||
                            (Math.abs(downY - motionEvent.getY()) > SCROLL_THRESHOLD)) {
                        isOnClick = false;
                        reactionSelector.getReactionSelectorListener().moveX(downX, motionEvent.getX());
                        reactionSelector.getReactionSelectorListener().moveY(downY, motionEvent.getY());
                        this.downX = motionEvent.getX();
                        this.downY = motionEvent.getY();
                    }
                    break;
                default:
                    break;

            }
            return true;
        }

    }


}
