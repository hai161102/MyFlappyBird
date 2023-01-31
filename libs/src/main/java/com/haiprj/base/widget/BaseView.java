package com.haiprj.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.haiprj.base.interfaces.EntityListener;

public abstract class BaseView extends View {

    protected BaseEntity entity;

    protected EntityListener entityListener;

    public BaseView(Context context, BaseEntity entity) {
        super(context);
        this.entity = entity;
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {

        if (entity == null){
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(entity.getWidth(), entity.getHeight());
        this.setLayoutParams(layoutParams);
        this.setX(entity.getX());
        this.setY(entity.getY());
        this.setBackgroundResource(entity.imageId);
    }

    public void setEntityListener(EntityListener entityListener) {
        this.entityListener = entityListener;
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);

    }

    public void update(BaseEntity another){
        draw(another);
        if (entity.getX() <= -entity.getWidth() * 2){
            remove();
        }
    }

    private void draw(BaseEntity another) {
        entity.setX(entity.worldX - another.worldX + another.getX());
        entity.setY(entity.worldY - another.worldY + another.getY());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        (((Activity) getContext())).runOnUiThread(() -> {
            this.setX(entity.getX());
            this.setY(entity.getY());
        });
    }
    protected void remove(){
        this.entityListener.onRemove();
    }

    public BaseEntity getEntity() {
        return entity;
    }
}
