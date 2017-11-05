package com.app.reactions_android.enums;


import android.widget.RelativeLayout;

public enum TEXT_RELATIVE_ALIGNMENT {
    RIGHT("right", RelativeLayout.RIGHT_OF), LEFT("left", RelativeLayout.LEFT_OF);
    String key;
    int value;

    TEXT_RELATIVE_ALIGNMENT(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public static TEXT_RELATIVE_ALIGNMENT getAlignment(String key) {
        TEXT_RELATIVE_ALIGNMENT result = null;
        for (TEXT_RELATIVE_ALIGNMENT align : TEXT_RELATIVE_ALIGNMENT.values()) {
            if (key.equals(align.key)) {
                result = align;
            }
        }
        return result;
    }
}
