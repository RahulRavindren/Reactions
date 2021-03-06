package com.app.reactions_android;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
    private Reaction SELECTED;
    private boolean isSelected = false;


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
        isSelected = selected;
    }

    private void inflateButton() {
        Logger.debug(TAG, "Inflating Button... ");
        ImageView button = new ImageView(getContext());
        button.setId(Integer.MIN_VALUE);
        //default pick
        Reaction defaultReaction = Reaction.LIKE;
        this.SELECTED = defaultReaction;
        Logger.debug(TAG, "Default reaction - " + defaultReaction.getTitle());
        button.setBackgroundResource(defaultReaction.getIcon());
        button.setColorFilter(isSelected ? ContextCompat.getColor(getContext(), attributeContainer.selectedColorRes) :
                ContextCompat.getColor(getContext(), attributeContainer.unselectedColorRes));
        LayoutParams params =
                new LayoutParams(ViewUtils.dpToPx(getContext(),
                        (int) attributeContainer.reactionIconWidth),
                        ViewUtils.dpToPx(getContext(), (int) attributeContainer.reactionIconHeight));
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
        textView.setId(Integer.MIN_VALUE + 1);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TEXT_RELATIVE_ALIGNMENT align = TEXT_RELATIVE_ALIGNMENT.getAlignment(attributeContainer.fontRelativeAlignment);
        switch (align) {
            case LEFT:
                params.addRule(RelativeLayout.LEFT_OF, getChildAt(0).getId());
                break;
            case RIGHT:
                params.addRule(RelativeLayout.RIGHT_OF, getChildAt(0).getId());
                break;
        }
        textView.setLayoutParams(params);
        textView.setTextColor(isSelected ? getResources().getColor(attributeContainer.selectedColorRes) :
                getResources().getColor(attributeContainer.unselectedColorRes));

        textView.setTextSize(attributeContainer.fontSize);
        textView.setText(SELECTED.getTitle());
        addView(textView);
    }


    //reaction to update when not default
    private void updateReaction(Reaction reaction) {
        this.SELECTED = reaction;
        ImageButton reactionButton = (ImageButton) getChildAt(0);
        reactionButton.setImageResource(reaction.getIcon());

        //text
        TextView reactionText = (TextView) getChildAt(1);
        reactionText.setText(reaction.getTitle());
        reactionText.setTextColor(getResources().getColor(reaction.getColor()));
        invalidate();

    }

    private void resetReaction() {

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
        private final float SCROLL_THRESHOLD = 3;
        private float downX;
        private float downY;
        private boolean isOnClick;
        private Rect viewRect;


        @Override
        public boolean onTouch(final View view, MotionEvent motionEvent) {
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
                    if (isOnClick) {
                        //onclick action
                        Logger.debug(TAG, "Onclick action on reaction ... ");
                        view.animate().scaleX((float) 1.5).scaleY((float) 1.5).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                view.animate().scaleX(1).scaleY(1).setDuration(300);
                            }
                        });

                    }
                    clear();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isOnClick || (Math.abs(downX - motionEvent.getX()) > SCROLL_THRESHOLD) ||
                            (Math.abs(downY - motionEvent.getY()) > SCROLL_THRESHOLD)) {
                        Logger.debug(TAG, "move action on reaction selector... ");
                        isOnClick = false;
                        if (viewRect != null) {
                            view.getGlobalVisibleRect(viewRect);
                            if (viewRect.contains(Math.round(view.getRootView().getX() + downX),
                                    Math.round(view.getY() + downY))) {
                                reactionFeedbackDidChanged(ReactionFeedback.SLIDE_FINGER_ACROSS);
                                new ReactionSelectorPopup(getContext(), reactionButtonDelegate, ReactionButton.this);
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
        private int selectedColorRes;
        private float reactionIconWidth;
        private float reactionIconHeight;

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
                    reactionIconHeight = a.getDimension(R.styleable.ReactionButton_RB_icon_height,
                            getResources().getDimension(R.dimen.icon_default_height));
                    reactionIconWidth = a.getDimension(R.styleable.ReactionButton_RB_icon_width,
                            getResources().getDimension(R.dimen.icon_default_width));
                } finally {
                    a.recycle();
                }

            }
        }
    }
}
