package com.app.reactions_android;


import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;

public final class Reaction {
    public static final Reaction LIKE = new Reaction("0", "Like",
            android.R.color.holo_blue_bright, android.R.drawable.ic_media_play,
            android.R.drawable.ic_media_play);
    final private String id;
    final private String title;
    final @IntegerRes
    private int color;
    @DrawableRes
    final private int icon;
    @DrawableRes
    final private int alternativeIcon;

    public Reaction(String id, String title, int color, int icon, int alternativeIcon) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.icon = icon;
        this.alternativeIcon = alternativeIcon;
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
