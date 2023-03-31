package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.haiprj.base.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Column {

    private final Context context;
    private final Random random;
    private final MyEntity[] myEntities;
    private final PlayerManager playerManager;

    private final int entityWidth;
    private final int viewWidth;
    private final int viewHeight;
    private final float spaceScale = 3f;
    private final boolean isPortrait;

    public Column(Context context, PlayerManager playerManager, int viewWidth, int viewHeight, boolean isPortrait) {
        this.context = context;
        this.playerManager = playerManager;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.isPortrait = isPortrait;
        random = new Random();
        myEntities = new MyEntity[10];
        entityWidth = (int) ((viewHeight * 32 / 268) / 1.5f);
        init();
    }

    private void init() {
        for (int i = 0; i < myEntities.length; i++) {
            myEntities[i] = getNew(i);
        }
    }

    public void draw(Canvas canvas) {
        for (MyEntity myEntity : myEntities) {
            RectF rectF = myEntity.getEntityRectF();
            if (rectF.left >= - (rectF.width() + 20)
            && rectF.right <= (rectF.width() + viewWidth)) {
                myEntity.draw(canvas);
            }

        }
    }

    private MyEntity getNew(int i) {
        float plusSpace = 0;
        plusSpace += viewWidth;
        if (i > 0){
            if (isPortrait){
                plusSpace = myEntities[i - 1].getWorldX() + viewWidth;
            }else
                plusSpace = myEntities[i - 1].getWorldX() + viewWidth / 4f;
        }
        float space = isPortrait ? viewHeight / 3.5f : viewHeight / 2.5f;
        return new MyEntity(
                context,
                entityWidth,
                entityWidth * 268 / 32,
                space - getMinusSpace(space),
                getHeightSpawnEntity(),
                plusSpace,
                this.viewWidth,
                this.viewHeight,
                playerManager,
                ()-> {
                    System.arraycopy(myEntities, 1, myEntities, 0, myEntities.length - 1);
                    myEntities[myEntities.length - 1] = getNew(myEntities.length - 1);
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

        float space = isPortrait ? viewHeight / 4 : viewHeight / 3;
        final RectF fixRect = new RectF();
        fixRect.left = 0f;
        fixRect.right = viewWidth;
        fixRect.top = viewHeight / 2 - space / 2;
        fixRect.bottom = fixRect.top + space;

        float range = fixRect.bottom - fixRect.top;
        float numberRand = random.nextInt((int) range);


        return numberRand + fixRect.top;
    }

    private MyEntity dieAt;
    public boolean hasIntersection() {

        for (MyEntity myEntity : myEntities) {
            if (myEntity.hasIntersectionEntity()) {
                dieAt = myEntity;
                return true;
            }
        }
        return false;
    }

    public MyEntity getDieAt() {
        if (dieAt != null) return dieAt;
        return null;
    }

    public void update() {
        for (MyEntity myEntity : myEntities) {
            if (myEntity.hasIntersectionX()) {
                playerManager.score++;
                break;
            }
        }
        for (MyEntity myEntity : myEntities) {
            myEntity.update();
        }
    }

    public void onGameOver() {

    }
}
