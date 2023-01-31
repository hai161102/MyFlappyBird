package com.haiprj.base.widget;

import android.graphics.Rect;
import android.graphics.RectF;

public abstract class BaseEntity {
    protected String name;
    protected int imageId;
    protected int width;
    protected int height;
    protected float x;
    protected float y;

    public float worldX = 0f;
    public float worldY = 0f;

    public BaseEntity(String name, int imageId, int width, int height, float x, float y) {
        this.name = name;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public BaseEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public Rect getRect() {
        return new Rect((int) x + 10, (int) y + 10, (int) (x + width) - 10, (int) (y + height) - 10);
    }

    public RectF getRectF() {
        return new RectF(x + 10f, y + 10f, (x + width) - 10f, (y + height) - 10f);
    }
}
