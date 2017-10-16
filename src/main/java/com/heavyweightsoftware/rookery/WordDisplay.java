package com.heavyweightsoftware.rookery;

import java.awt.*;

public class WordDisplay {
    private AnimationType animationType;
    private int animationSpeed;

    private String text;
    private Color textBackground;
    private Color textForeground;
    private String textFontName;
    private int textFontSize;

    private String title;
    private Color titleBackground;
    private Color titleForeground;
    private String titleFontName;
    private int titleFontSize;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getTextBackground() {
        return textBackground;
    }

    public void setTextBackground(Color textBackground) {
        this.textBackground = textBackground;
    }

    public Color getTextForeground() {
        return textForeground;
    }

    public void setTextForeground(Color textForeground) {
        this.textForeground = textForeground;
    }

    public String getTextFontName() {
        return textFontName;
    }

    public void setTextFontName(String textFontName) {
        this.textFontName = textFontName;
    }

    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getTitleBackground() {
        return titleBackground;
    }

    public void setTitleBackground(Color titleBackground) {
        this.titleBackground = titleBackground;
    }

    public Color getTitleForeground() {
        return titleForeground;
    }

    public void setTitleForeground(Color titleForeground) {
        this.titleForeground = titleForeground;
    }

    public String getTitleFontName() {
        return titleFontName;
    }

    public void setTitleFontName(String titleFontName) {
        this.titleFontName = titleFontName;
    }

    public int getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(int titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}
