package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.haiprj.base.interfaces.EntityListener;
import com.haiprj.base.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Column {

    private final Context context;
    private final Random random;
    private final DoubleEntity[] doubleEntities;
    private final PlayerManager playerManager;

    private final int entityWidth;
    private final int viewWidth;
    private final int viewHeight;
    private final float spaceScale = 3f;

    public Column(Context context, PlayerManager playerManager, int viewWidth, int viewHeight) {
        this.context = context;
        this.playerManager = playerManager;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        random = new Random();
        doubleEntities = new DoubleEntity[10];
        entityWidth = ((int) GameUtils.pxFromDp(this.context, 56));
        init();
    }

    private void init() {
        for (int i = 0; i < doubleEntities.length; i++) {
            doubleEntities[i] = getNew(i);
        }
    }

    public void draw(Canvas canvas) {
        for (DoubleEntity doubleEntity : doubleEntities) {
            doubleEntity.run(playerManager);
            if (doubleEntity.getTopE().worldX >= - doubleEntity.getTopE().getWidth() * spaceScale && doubleEntity.getTopE().worldX <= viewWidth + doubleEntity.getTopE().getWidth() * 3f)
                doubleEntity.draw(canvas);
        }
    }

    private DoubleEntity getNew(int i) {
        float plusSpace = 0;
        plusSpace += viewWidth;
        if (i > 0){
            plusSpace = doubleEntities[i - 1].getTopE().getX() + entityWidth * spaceScale;
        }
        float space = viewHeight / 2.5f;
        return new DoubleEntity(
                context,
                space - getMinusSpace(space),
                getHeightSpawnEntity(),
                plusSpace,
                entityWidth,
                this.viewHeight,
                () -> {
                    System.arraycopy(doubleEntities, 1, doubleEntities, 0, doubleEntities.length - 1);
                    doubleEntities[doubleEntities.length - 1] = getNew(doubleEntities.length - 1);
                }
        );
    }
    private int getRandomPositionSpawn(int length, int startPoint){
        int pos = random.nextInt(length - startPoint) + startPoint;
        return pos;
    }


    public float getMinusSpace (float space){
        float oOT = space / 10f;

        return random.nextInt((int) (oOT * 2));
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private float getHeightSpawnEntity(){

        final RectF fixRect = new RectF();
        fixRect.left = 0f;
        fixRect.right = viewWidth;
        fixRect.top = viewHeight / 3;
        fixRect.bottom = viewHeight - fixRect.top;

        float range = fixRect.bottom - fixRect.top;
        float numberRand = random.nextInt((int) range);


        return numberRand + fixRect.top;
    }
}
