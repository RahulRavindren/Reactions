package com.app.reactions_android.delegate;


import com.app.reactions_android.Reaction;
import com.app.reactions_android.listeners.ReactionSelectorFeedbackImpl;

import java.util.LinkedList;

public class ReactionButtonDelegate {
    private LinkedList<ReactionSelectorFeedbackImpl> reactionSelectorFeedbacks = new LinkedList<>();

    public LinkedList<ReactionSelectorFeedbackImpl> getReactionSelectorFeedbacks() {
        return reactionSelectorFeedbacks;
    }

    public void setReactionSelectorFeedbacks(LinkedList<ReactionSelectorFeedbackImpl> reactionSelectorFeedbacks) {
        this.reactionSelectorFeedbacks = reactionSelectorFeedbacks;
    }

    public void hoveredReaction(Reaction reaction) {
        for (ReactionSelectorFeedbackImpl reactionSelectorFeedback : reactionSelectorFeedbacks) {
            reactionSelectorFeedback.hoveredReaction(reaction);
        }
    }
}
