package com.app.reactions_android;


import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;

public final class Reaction {
    public static final Reaction LIKE = new Reaction("0", "Like", android.R.color.white, "like.json");
    public static final Reaction ANGRY = new Reaction("1", "Angry", android.R.color.white, "angry.json");
    public static final Reaction HAHA = new Reaction("2", "Haha", android.R.color.white, "haha.json");
    public static final Reaction LOVE = new Reaction("3", "Love", android.R.color.white, "love.json");
    public static final Reaction SAD = new Reaction("4", "Sad", android.R.color.white, "sad.json");
    public static final Reaction WOW = new Reaction("5", "Wow", android.R.color.white, "wow.json");


    public static final Reaction[] DEFAULT_LIST_LOTTIE = new Reaction[]{LIKE, ANGRY, HAHA, LOVE, SAD, WOW};


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

    public String getGifPath() {
        return gifPath;
    }

    public boolean isLottie() {
        return gifPath != null;
    }

}
