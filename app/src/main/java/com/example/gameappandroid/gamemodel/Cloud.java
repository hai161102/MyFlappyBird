package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.gameappandroid.R;
import com.haiprj.base.interfaces.EntityListener;
import com.haiprj.base.widget.BaseEntity;

import java.util.Arrays;

public class Cloud {

    private final Context context;

    private final int width = 128;
    private final EntityManager[] entityManagers;
    private final int maxWidth;
    private final int maxHeight;
    private final PlayerManager playerManager;


    @SuppressWarnings("SuspiciousNameCombination")
    public Cloud(Context context, int maxWidth, int maxHeight, PlayerManager playerManager) {
        this.context = context;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.playerManager = playerManager;
        int length = this.maxWidth / width + 10;
        entityManagers = new EntityManager[length];
        float x = 0;
        float y = maxHeight - width / 2f;
        for (int i = 0; i < entityManagers.length; i++) {
            x = i * width;
            entityManagers[i] = getNew(x, y);
        }

    }

    public EntityManager getNew(float x, float y) {
        EntityManager entityManager = new EntityManager(
                this.context,
                "cloud" + x,
                new int[] {
                        R.drawable.cloud
                },
                width,
                width,
                x,
                y
        );
        entityManager.worldX = entityManager.getX();
        entityManager.worldY = entityManager.getY();
        return entityManager;
    }
    public void draw(Canvas canvas) {

        for (int i = 0; i < entityManagers.length; i++) {
            EntityManager entityManager = entityManagers[i];
            canvas.drawBitmap(entityManager.getBitmap(), entityManager.worldX, entityManager.worldY, null);
            entityManager.worldX = entityManager.getX() - playerManager.worldX;
            if (entityManager.worldX < - entityManager.getWidth() * 2f) {
                entityManagers[i] = null;
                sortArrayWhenRemove();
                entityManagers[entityManagers.length - 1] = getNew(entityManagers[entityManagers.length - 2].getRectF().right, entityManagers[entityManagers.length - 2].getY());
            }
        }
    }

    private void sortArrayWhenRemove() {
        for (int i = 0; i < entityManagers.length; i++) {
            if (entityManagers[i] == null) {
                if (entityManagers.length - 1 - i >= 0)
                    System.arraycopy(entityManagers, i + 1, entityManagers, i, entityManagers.length - 1 - i);
                break;
            }
        }
    }
}
