package com.heavyweightsoftware.rookery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;
import java.util.ArrayList;

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

    @JsonIgnore
    public String[] getTextArray() {
        ArrayList<String> list = new ArrayList<>();

        final int stateLookingForChar = 1;
        final int stateLookingForDelim = 2;
        int state = stateLookingForDelim;

        char[] chars = getText().toCharArray();
        int idx = 0;
        StringBuilder sb = new StringBuilder();

        while (idx < chars.length) {
            char ch = chars[idx];
            switch (state) {
                case stateLookingForChar:
                    if(Character.isAlphabetic(ch)) {
                        list.add(sb.toString());
                        sb.setLength(0);
                        state = stateLookingForDelim;
                    }
                    sb.append(ch);
                    idx++;
                    break;

                case stateLookingForDelim:
                    sb.append(ch);
                    idx++;
                    if(!Character.isAlphabetic(ch)) {
                        state = stateLookingForChar;
                    }
                    break;

                default:
                    throw new IllegalStateException("Invalid state:" + state);
            }

        }

        if(sb.length() > 0) {
            list.add(sb.toString());
        }

        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonIgnore
    public Color getTextBackground() {
        return textBackground;
    }

    @JsonIgnore
    public void setTextBackground(Color textBackground) {
        this.textBackground = textBackground;
    }

    public int getTextBackgroundRgb() {
        int result = 0;

        if(textBackground != null) {
            result = textBackground.getRGB();
        }

        return result;
    }

    public void setTextBackgroundRgb(int rgb) {
        this.textBackground = new Color(rgb);
    }

    @JsonIgnore
    public Color getTextForeground() {
        return textForeground;
    }

    @JsonIgnore
    public void setTextForeground(Color textForeground) {
        this.textForeground = textForeground;
    }

    public int getTextForegroundRgb() {
        int result = 0;

        if(textForeground != null) {
            result = textForeground.getRGB();
        }

        return result;
    }

    public void setTextForegroundRgb(int rgb) {
        this.textForeground = new Color(rgb);
    }

    @JsonIgnore
    public Font getTextFont() {
        Font textFont = new Font(getTextFontName(), Font.PLAIN, getTextFontSize());
        return textFont;
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

    @JsonIgnore
    public Color getTitleBackground() {
        return titleBackground;
    }

    @JsonIgnore
    public void setTitleBackground(Color titleBackground) {
        this.titleBackground = titleBackground;
    }

    public int getTitleBackgroundRgb() {
        int result = 0;

        if(titleBackground != null) {
            result = titleBackground.getRGB();
        }

        return result;
    }

    public void setTitleBackgroundRgb(int rgb) {
        this.titleBackground = new Color(rgb);
    }

    @JsonIgnore
    public Color getTitleForeground() {
        return titleForeground;
    }

    @JsonIgnore
    public void setTitleForeground(Color titleForeground) {
        this.titleForeground = titleForeground;
    }

    public int getTitleForegroundRgb() {
        int result = 0;

        if(titleForeground != null) {
            result = titleForeground.getRGB();
        }

        return result;
    }

    public void setTitleForegroundRgb(int rgb) {
        this.titleForeground = new Color(rgb);
    }


    @JsonIgnore
    public Font getTitleFont() {
        Font titleFont = new Font(getTitleFontName(), Font.PLAIN, getTitleFontSize());
        return titleFont;
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
