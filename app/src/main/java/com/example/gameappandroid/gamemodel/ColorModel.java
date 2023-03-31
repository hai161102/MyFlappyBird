package com.example.gameappandroid.gamemodel;

public class ColorModel {
    private int resColor;
    private boolean isSelect;

    public ColorModel(int resColor, boolean isSelect) {
        this.resColor = resColor;
        this.isSelect = isSelect;
    }

    public ColorModel() {
    }

    public int getResColor() {
        return resColor;
    }

    public void setResColor(int resColor) {
        this.resColor = resColor;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
