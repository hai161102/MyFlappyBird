package com.example.gameappandroid.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.haiprj.base.utils.GameUtils;

public class GameTexture extends TextureView implements TextureView.SurfaceTextureListener {
    private PlayerManager playerManager;
    private Canvas canvas;
    private Paint paint;

    public GameTexture(@NonNull Context context, PlayerManager playerManager) {
        super(context);
        this.playerManager = playerManager;
        init();
    }

    public GameTexture(@NonNull Context context, @Nullable AttributeSet attrs, PlayerManager playerManager) {
        super(context, attrs);
        this.playerManager = playerManager;
        init();
    }

    public GameTexture(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, PlayerManager playerManager) {
        super(context, attrs, defStyleAttr);
        this.playerManager = playerManager;
        init();
    }

    public GameTexture(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, PlayerManager playerManager) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        this.setLayoutParams(layoutParams);
        this.setX(playerManager.getX());
        this.setY(playerManager.getY());

        this.setSurfaceTextureListener(this);
        this.setOpaque(false);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void update(){
        this.setY(playerManager.getY());
        invalidate();
    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        canvas = this.lockCanvas(playerManager.getRect());
        paint = new Paint();
        paint.setColor(0xff00ff00);
        try {
            canvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
            canvas.drawRect(playerManager.getX(), playerManager.getY(), playerManager.getX() + playerManager.getWidth(), playerManager.worldY + playerManager.getY(), paint);
        } finally {
            this.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

    }
}
