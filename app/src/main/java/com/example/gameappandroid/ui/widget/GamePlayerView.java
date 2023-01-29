package com.example.gameappandroid.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.gameutils.GameUtils;

public class GamePlayerView extends androidx.appcompat.widget.AppCompatImageView {

    private PlayerManager playerManager;

    public GamePlayerView(@NonNull Context context) {
        super(context);
        init();
    }

    public GamePlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GamePlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GamePlayerView(@NonNull Context context, PlayerManager playerManager) {
        super(context);
        this.playerManager = playerManager;
        init();
    }

    public GamePlayerView(@NonNull Context context, @Nullable AttributeSet attrs, PlayerManager playerManager) {
        super(context, attrs);
        this.playerManager = playerManager;
        init();
    }

    public GamePlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, PlayerManager playerManager) {
        super(context, attrs, defStyleAttr);
        this.playerManager = playerManager;
        init();
    }

    private void init() {
        if (playerManager == null){
            playerManager = new PlayerManager(
                    "Unknown",
                    R.drawable.ic_launcher_background,
                    (int) GameUtils.pxFromDp(getContext(), 20),
                    (int) GameUtils.pxFromDp(getContext(), 20),
                    0,
                    0,
                    5
            );
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(playerManager.getWidth(), playerManager.getHeight());
        this.setX(playerManager.getX());
        this.setY(playerManager.getY());
        this.setImageResource(playerManager.getImageId());
        this.setLayoutParams(layoutParams);
        this.setPadding(0, 0, 0, 0);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void update(){
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                GamePlayerView.this.setY(playerManager.getY());
                if (playerManager.canDown) {
                    setRotation(30f);
                }
                else if (playerManager.canUp){
                    setRotation(-30f);
                }
                else setRotation(0f);
            }
        });
    }
}
