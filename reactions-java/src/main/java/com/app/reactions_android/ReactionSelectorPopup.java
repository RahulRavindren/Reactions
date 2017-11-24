package com.app.reactions_android;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.app.reactions_android.delegate.ReactionButtonDelegate;

import java.lang.ref.WeakReference;

/**
 * @Author rahulravindran
 */

public final class ReactionSelectorPopup {
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
        window.showAtLocation(anchorView.get(), Gravity.CENTER, 0, 0);
        window.setElevation(5);
        window.setBackgroundDrawable(context.get().getResources().getDrawable(R.drawable.selector_oval_background));
    }


}
