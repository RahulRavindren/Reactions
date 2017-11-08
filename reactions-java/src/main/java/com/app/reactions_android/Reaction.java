package com.app.reactions_android;


import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;

public final class Reaction {
    public static final Reaction LIKE = new Reaction("0", "Like",
            android.R.color.holo_blue_bright, android.R.drawable.ic_media_play,
            android.R.drawable.ic_media_play);

    public static final Reaction[] DEFAULT_LIST = new Reaction[]{LIKE};


    final private String id;
    final private String title;
    final @IntegerRes
    private int color;
    @DrawableRes
    final private int icon;
    @DrawableRes
    final private int alternativeIcon;
    final private String gifPath;


    public Reaction(String id, String title, int color, int icon, int alternativeIcon) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.icon = icon;
        this.alternativeIcon = alternativeIcon;
        this.gifPath = null;
    }

    public Reaction(String id, String title, int color, String gifPath) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.gifPath = gifPath;
        this.icon = 0;
        this.alternativeIcon = -1;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    @DrawableRes
    public int getAlternativeIcon() {
        return alternativeIcon;
    }

    @Override
    public boolean equals(Object obj) {
        Reaction reaction = (Reaction) obj;
        return reaction.id == this.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "<Reaction id=" + id + " title=" + title;
    }


}
