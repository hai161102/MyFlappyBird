package com.example.gameappandroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.haiprj.base.utils.GameUtils;
import com.haiprj.base.widget.BaseEntity;
import com.haiprj.base.widget.BaseView;

public class GameView extends BaseView {
    public GameView(Context context, BaseEntity entity) {
        super(context, entity);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        if (entity == null){
            entity = new EntityManager(
                    "",
                    R.drawable.ic_launcher_background,
                    (int) GameUtils.pxFromDp(getContext(), 24),
                    (int) GameUtils.pxFromDp(getContext(), 24),
                    0f,
                    0f
            );
            entity.worldX = 0f;
            entity.worldY = 0f;
        }
        super.init();
        //this.setBackgroundColor(Color.BLACK);

    }


}
