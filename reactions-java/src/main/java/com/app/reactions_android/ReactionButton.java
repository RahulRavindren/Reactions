package com.app.reactions_android;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.reactions_android.delegate.ReactionButtonDelegate;
import com.app.reactions_android.enums.ReactionFeedback;
import com.app.reactions_android.enums.TEXT_RELATIVE_ALIGNMENT;
import com.app.reactions_android.listeners.ReactionFeedbackImpl;
import com.app.reactions_android.listeners.ReactionSelectorFeedbackImpl;

public final class ReactionButton extends RelativeLayout
        implements ReactionFeedbackImpl, ReactionSelectorFeedbackImpl {
    public static final String TAG = ReactionButton.class.getSimpleName();
    //launch reaction selector
    ReactionSelector reactionSelector;
    private ImageView iconImageView;
    private TextView titleLabel;
    private AttributeContainer attributeContainer;
    private ReactionButtonDelegate reactionButtonDelegate;


    public ReactionButton(Context context) {
        this(context, null);
    }

    public ReactionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReactionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //avoid editor not rendering custom view
        if (!isInEditMode()) {
            //container
            attributeContainer = new AttributeContainer(context, attrs);

            //delegate
            reactionButtonDelegate = new ReactionButtonDelegate();

            //create reaction selector
            reactionSelector = new ReactionSelector(context, attrs, reactionButtonDelegate);

            setup();
        }
    }

    private void setup() {
        GestureRecogniser recogniser = new GestureRecogniser();
        setOnTouchListener(recogniser);

        //inflate view
        inflateButton();
        inflateText();
    }

    @Override
    public void setSelected(boolean selected) {
        attributeContainer.isSelected = selected;
    }

    private void inflateButton() {
        Logger.debug(TAG, "Inflating Button... ");
        ImageButton button = new ImageButton(getContext());
        //default pick
        Reaction defaultReaction = Reaction.LIKE;
        Logger.debug(TAG, "Default reaction - " + defaultReaction.getTitle());
        button.setBackground(null);
        button.setImageResource(
                attributeContainer.isSelected ? defaultReaction.getIcon() : defaultReaction.getAlternativeIcon());
        //button.setImageTintList(attributeContainer.isSelected ? new ColorStateList(attributeContainer.selectedColorRes) : new ColorStateList(attributeContainer.unselectedColorRes));
        RelativeLayout.LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TEXT_RELATIVE_ALIGNMENT align = TEXT_RELATIVE_ALIGNMENT.getAlignment(attributeContainer.fontRelativeAlignment);
        switch (align) {
            case RIGHT:
                params.rightMargin = (int) attributeContainer.fontIconSpacing;
                break;
            case LEFT:
                params.leftMargin = (int) attributeContainer.fontIconSpacing;
        }
        button.setLayoutParams(params);
        addView(button);
    }

    private void inflateText() {
        Logger.debug(TAG, "Inflating textview... ");
        TextView textView = new TextView(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TEXT_RELATIVE_ALIGNMENT align = TEXT_RELATIVE_ALIGNMENT.getAlignment(attributeContainer.fontRelativeAlignment);
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
        textView.setTextColor(attributeContainer.isSelected ?
                getResources().getColor(android.R.color.darker_gray) :
                getResources().getColor(attributeContainer.selectedColorRes));

        textView.setTextSize(attributeContainer.fontSize);
        addView(textView);
    }

    private void updateReaction(Reaction reaction) {
        ImageButton reactionButton = (ImageButton) getChildAt(0);
        reactionButton.setImageResource(reaction.getIcon());
        invalidate();

        //text
        TextView reactionText = (TextView) getChildAt(1);
        reactionText.setText(reaction.getTitle());
        reactionText.setTextColor();
    }

    @Override
    public void reactionFeedbackDidChanged(ReactionFeedback feedback) {
        Logger.debug(TAG, "Reaction feedback changed to  ----- " + feedback.name());
    }

    @Override
    public void hoveredReaction(final @Nullable Reaction reaction) {
        if (reaction != null) {
            //apply reaction

        }
    }

    class GestureRecogniser implements View.OnTouchListener {
        private final float SCROLL_THRESHOLD = 10;
        private float downX;
        private float downY;
        private boolean isOnClick;
        private Rect viewRect;


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    isOnClick = true;
                    clear();
                    viewRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
                case MotionEvent.ACTION_UP:
                    if (isOnClick && view.equals(this)) {
                        //onclick action
                        Logger.debug(TAG, "Onclick action on reaction ... ");
                        ObjectAnimator.ofFloat(view, "scaleX", 1.8f, 1.8f)
                                .setDuration(300).start();
                    }
                    clear();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isOnClick && (Math.abs(downX - motionEvent.getX()) > SCROLL_THRESHOLD) ||
                            (Math.abs(downY - motionEvent.getY()) > SCROLL_THRESHOLD)) {
                        Logger.debug(TAG, "move action on reaction selector... ");
                        isOnClick = false;
                        if (viewRect != null) {
                            view.getHitRect(viewRect);
                            if (viewRect.contains(Math.round(view.getRootView().getX() + downX),
                                    Math.round(view.getY() + downY))) {
                                reactionFeedbackDidChanged(ReactionFeedback.SLIDE_FINGER_ACROSS);
                                reactionSelector.getReactionSelectorListener()
                                        .move(downX, motionEvent.getX(), downY, motionEvent.getY());
                            } else {
                                reactionFeedbackDidChanged(ReactionFeedback.RELEASE_TO_CANCEL);
                            }

                        }
                        this.downX = motionEvent.getX();
                        this.downY = motionEvent.getY();
                    }
                    break;
                default:
                    break;

            }
            return true;
        }

        private void clear() {
            viewRect = null;
        }

    }

    protected class AttributeContainer {
        private float fontIconSpacing;
        private float fontSize;
        private int unselectedColorRes;
        private String fontRelativeAlignment;
        private boolean isSelected;
        private int selectedColorRes;


        public AttributeContainer(@NonNull final Context context,
                                  @Nullable final AttributeSet attributeSet) {
            if (attributeSet != null) {
                TypedArray a = getContext().getTheme()
                        .obtainStyledAttributes(attributeSet, R.styleable.ReactionButton, 0, 0);
                try {
                    fontIconSpacing = a.getDimension(R.styleable.ReactionButton_RB_text_icon_spacing,
                            (int) getContext().getResources().getDimension(R.dimen.font_icon_spacing));
                    fontSize = a.getDimension(R.styleable.ReactionButton_RB_font_size, 20);
                    unselectedColorRes =
                            a.getResourceId(R.styleable.ReactionButton_RB_unselected_tint_color, Color.GRAY);
                    selectedColorRes = a.getResourceId(R.styleable.ReactionButton_RB_selected_tint_color, Color.BLUE);
                    fontRelativeAlignment = a.getString(R.styleable.ReactionButton_RB_text_icon_alignment);
                } finally {
                    a.recycle();
                }

            }
        }
    }
}
