package com.haiprj.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseEntityView extends FrameLayout {

    protected BaseEntity baseEntity;

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
        baseEntity.setX(baseEntity.worldX - entity.worldX + entity.getX());
        baseEntity.setY(baseEntity.worldY - entity.worldY + entity.getY());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseEntityView.this.setX(baseEntity.getX());
                BaseEntityView.this.setY(baseEntity.getY());
            }
        });

    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }
}
