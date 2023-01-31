package com.example.gameappandroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgress extends ProgressBar {

    public int timeInSecond;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        this.setProgress(100);
    }
}
