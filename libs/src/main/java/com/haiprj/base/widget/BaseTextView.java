package com.haiprj.base.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseTextView extends androidx.appcompat.widget.AppCompatTextView {
    public BaseTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init(){
        this.setTypeface(Typeface.MONOSPACE);
    }
}
