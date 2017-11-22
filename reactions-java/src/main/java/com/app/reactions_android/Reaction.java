package com.app.reactions_android;


import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;

public final class Reaction {
    public static final Reaction LIKE_GIF = new Reaction("0", "Like", android.R.color.white, "like.json");
    public static final Reaction ANGRY_GIF = new Reaction("1", "Angry", android.R.color.white, "angry.json");
    public static final Reaction HAHA_GIF = new Reaction("2", "Haha", android.R.color.white, "haha.json");
    public static final Reaction LOVE_GIF = new Reaction("3", "Love", android.R.color.white, "love.json");
    public static final Reaction SAD_GIF = new Reaction("4", "Sad", android.R.color.white, "sad.json");
    public static final Reaction WOW_GIF = new Reaction("5", "Wow", android.R.color.white, "wow.json");


    public static final Reaction LIKE = new Reaction("0", "Like", android.R.color.white, R.drawable.like2x);
    public static final Reaction ANGRY = new Reaction("1", "Angry", android.R.color.white, R.drawable.angry);
    public static final Reaction HAHA = new Reaction("2", "Haha", android.R.color.white, R.drawable.haha);
    public static final Reaction LOVE = new Reaction("3", "Love", android.R.color.white, R.drawable.love);
    public static final Reaction SAD = new Reaction("4", "Sad", android.R.color.white, R.drawable.sad);
    public static final Reaction WOW = new Reaction("5", "Wow", android.R.color.white, R.drawable.wow);


    public static final Reaction[] DEFAULT_LIST = new Reaction[]{LIKE, ANGRY, HAHA, LOVE, SAD, WOW};
    public static final Reaction[] DEFAULT_LIST_LOTTIE = new Reaction[]{LIKE_GIF, ANGRY_GIF, HAHA_GIF, LOVE_GIF, SAD_GIF, WOW_GIF};

    final private String id;
    final private String title;
    final @IntegerRes
    private int color;
    @DrawableRes
    final private int icon;
    final private String gifPath;


    public Reaction(String id, String title, int color, int icon) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.icon = icon;
        this.gifPath = null;
    }

    public Reaction(String id, String title, int color, String gifPath) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.gifPath = gifPath;
        this.icon = 0;
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
