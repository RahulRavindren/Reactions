package com.app.reactions_android;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.app.reactions_android.delegate.ReactionButtonDelegate;

import java.lang.ref.WeakReference;

/**
 * @Author rahulravindran
 */

public final class ReactionSelectorPopup {
    public static final String TAG_NAME = ReactionSelectorPopup.class.getSimpleName();
    private final WeakReference<Context> context;
    private final ReactionButtonDelegate reactionDelegate;
    private PopupWindow window;
    private WeakReference<View> anchorView;


    public ReactionSelectorPopup(final Context context,
                                 final ReactionButtonDelegate reactionButtonDelegate,
                                 final View anchorView) {
        this.context = new WeakReference<Context>(context);
        this.reactionDelegate = reactionButtonDelegate;
        this.anchorView = new WeakReference<View>(anchorView);
        init();
    }

    private void init() {
        //init reactionselector
        ReactionSelector selector = new ReactionSelector(context.get(), reactionDelegate);
        window = new PopupWindow(selector, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setOutsideTouchable(true);
        int[] location = new int[2];
        anchorView.get().getLocationOnScreen(location);
        window.showAtLocation(anchorView.get(), Gravity.CENTER_HORIZONTAL, 0, -20);
        window.setBackgroundDrawable(context.get().getResources().getDrawable(R.drawable.rounded_rect_bg_shadow));
        window.setTouchInterceptor(new ReactionGuesterListener());
    }


    public class ReactionGuesterListener implements View.OnTouchListener {
        private float downX;
        private float downY;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    Rect r = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    view.getHitRect(r);
                    if (r.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        Logger.debug(TAG_NAME, "View contains x and y" + view);
                    }
                    Logger.debug(TAG_NAME, "MotionEvent X :: " + String.valueOf(motionEvent.getX()) + "  MotionEvent Y :: " + String.valueOf(motionEvent.getY()));
                    break;
            }

            return false;


        }

    }


}
