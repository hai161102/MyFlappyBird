package com.haiprj.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.base.interfaces.EntityListener;

public abstract class BaseEntityView extends FrameLayout {

    protected BaseEntity baseEntity;
    protected EntityListener entityListener;

    public void setEntityListener(EntityListener entityListener) {
        this.entityListener = entityListener;
    }

    public BaseEntityView(@NonNull Context context, BaseEntity baseEntity) {
        super(context);
        this.baseEntity = baseEntity;
        init();
    }

    public BaseEntityView(@NonNull Context context, @Nullable AttributeSet attrs, BaseEntity baseEntity) {
        super(context, attrs);
        this.baseEntity = baseEntity;
        init();
    }

    public BaseEntityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, BaseEntity baseEntity) {
        super(context, attrs, defStyleAttr);
        this.baseEntity = baseEntity;
        init();
    }

    public BaseEntityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, BaseEntity baseEntity) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.baseEntity = baseEntity;
        init();
    }

    protected void init(){
        this.setX(baseEntity.getX());
        this.setY(baseEntity.getY());
    }

    public void update(BaseEntity entity){
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawUI();
            }
        });

    }

    protected abstract void drawUI();

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }
}
