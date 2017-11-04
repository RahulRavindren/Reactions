package com.app.reactions_android;


import android.graphics.drawable.Drawable;
import android.support.annotation.IntegerRes;

public final class Reaction {
    final private String id;
    final private String title;
    final @IntegerRes private int color;
    final private Drawable icon;
    final private Drawable alternativeIcon;


    public Reaction(String id, String title, int color, Drawable icon, Drawable alternativeIcon) {
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

    public Drawable getIcon() {
        return icon;
    }

    public Drawable getAlternativeIcon() {
        return alternativeIcon;
    }

    @Override
    public boolean equals(Object obj){
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
