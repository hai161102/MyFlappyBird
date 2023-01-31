package com.haiprj.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseGameFrame extends FrameLayout implements View.OnTouchListener {

    protected final int FPS = 60;
    protected boolean isGameOver = false;
    protected Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isGameOver){
                update();
                BaseGameFrame.this.invalidate();
            }
            BaseGameFrame.this.postDelayed(this, 1000 / FPS);

        }
    };

    protected void update() {

    }

    public BaseGameFrame(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseGameFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseGameFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseGameFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init(){
        this.setOnTouchListener(this);
    }

    public void start(){
        post(runnable);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                onTouchDown(view);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(view);
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(view);
                break;
        }
        return true;
    }

    protected void onTouchUp(View view) {

    }

    protected void onTouchMove(View view) {

    }

    protected void onTouchDown(View view) {
    }

    public void destroy(){
        removeCallbacks(runnable);
    }
}
