package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.gameappandroid.R;
import com.haiprj.base.interfaces.EntityListener;

public class DoubleEntity {

    private final Context context;
    private EntityManager topE;
    private EntityManager bottomE;
    private final float spaceHeight;
    private final float centerSpawn;
    private final float x;
    private final int width;
    private final int maxHeight;

    private final Paint paint;

    private int entityHeight;
    private final EntityListener entityListener;
    public DoubleEntity(Context context, float spaceHeight, float centerSpawn, float x, int width, int maxHeight, EntityListener entityListener) {
        this.context = context;
        this.spaceHeight = spaceHeight;
        this.centerSpawn = centerSpawn;
        this.x = x;
        this.width = width;
        this.maxHeight = maxHeight;
        this.entityListener = entityListener;
        paint = new Paint();
        paint.setShadowLayer(4f, 0f, 0f, Color.BLACK);
        entityHeight = (int) (width * 22.5f / 2);
        init();

    }

    private void init() {
        float divider = this.spaceHeight / 2f;
        int topHeight = (int) (centerSpawn - divider);
        topE = new EntityManager(
                this.context,
                "entity",
                new int[] {R.drawable.col},
                this.width,
                entityHeight,
                this.x,
                0f - (entityHeight - topHeight)
        );
        topE.worldX = topE.getX();
        bottomE = new EntityManager(
                this.context,
                "entity_b",
                new int[] {
                        R.drawable.col
                },
                this.width,
                entityHeight,
                this.x,
                centerSpawn + divider
        );
        bottomE.worldX = bottomE.getX();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(topE.getBitmap(), topE.worldX, topE.getY(), null);
        canvas.drawBitmap(bottomE.getBitmap(), bottomE.worldX, bottomE.getY(), null);
    }

    public void run(PlayerManager playerManager) {
        topE.worldX = bottomE.worldX = topE.getX() - playerManager.worldX;
        if (topE.worldX < - topE.getWidth() * 4f) {
            entityListener.onRemove();
        }
    }
    public EntityManager getTopE() {
        return topE;
    }

    public EntityManager getBottomE() {
        return bottomE;
    }
}
