package com.app.reactions_android.enums;


public enum ReactionFeedback {
    SLIDE_FINGER_ACROSS, RELEASE_TO_CANCEL, TAP_TO_SELECT_REACTION;

    public String localizedString(ReactionFeedback feedback) {
        switch (feedback) {
            case SLIDE_FINGER_ACROSS:
                return "feedback.slideFingerAcross";
            case RELEASE_TO_CANCEL:
                return "feedback.releaseToCancel";
            case TAP_TO_SELECT_REACTION:
                return "feedback.tapToSelectReaction";
            default:
                return "";
        }
    }
}
