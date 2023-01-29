package com.example.gameappandroid.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.gameutils.GameUtils;

public class GameImageView extends androidx.appcompat.widget.AppCompatImageView {

    private EntityManager entityManager;
    private ViewGroup parent;
    private EntityListener listener;

    public void setListener(EntityListener listener) {
        this.listener = listener;
    }

    public GameImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public GameImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GameImageView(@NonNull Context context, EntityManager entityManager) {
        super(context);
        this.entityManager = entityManager;
        init();
    }

    public GameImageView(@NonNull Context context, @Nullable AttributeSet attrs, EntityManager entityManager) {
        super(context, attrs);
        this.entityManager = entityManager;
        init();
    }

    public GameImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, EntityManager entityManager) {
        super(context, attrs, defStyleAttr);
        this.entityManager = entityManager;
        init();
    }

    private void init() {
        if (entityManager == null){
            entityManager = new EntityManager(
                    "",
                    R.drawable.ic_launcher_background,
                    (int) GameUtils.pxFromDp(getContext(), 24),
                    (int) GameUtils.pxFromDp(getContext(), 24),
                    0f,
                    0f
            );
            entityManager.worldX = 0f;
            entityManager.worldY = 0f;
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(entityManager.getWidth(), entityManager.getHeight());
        this.setLayoutParams(layoutParams);
        this.setX(entityManager.getX());
        this.setY(entityManager.getY());
        this.setImageResource(entityManager.getImageId());
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(entityManager.getWidth(), entityManager.getHeight());
        layoutParams.setMargins(10, 10, 10, 10);
        this.setLayoutParams(layoutParams);
        //this.setPadding(10, 10, 10, 10);
        this.setX(entityManager.getX());
        this.setY(entityManager.getY());
        this.setImageResource(entityManager.getImageId());
        invalidate();
    }

    private void draw(PlayerManager playerManager){
        entityManager.setX(entityManager.worldX - playerManager.worldX + playerManager.getX());
        entityManager.setY(entityManager.worldY - playerManager.worldY + playerManager.getY());
        invalidate();
        //Log.d("DrawEntity", "draw: worldX: " + entityManager.worldX + ", worldY: " + entityManager.worldY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        (((Activity) getContext())).runOnUiThread(() -> {
            this.setX(entityManager.getX());
            this.setY(entityManager.getY());
        });
    }

    public void update(PlayerManager playerManager){
        draw(playerManager);
        if (entityManager.getX() <= -entityManager.getWidth() * 2){
            remove();
        }
    }

    private void remove() {
        listener.onRemove();
    }

    public interface EntityListener{
        void onRemove();
    }

}
