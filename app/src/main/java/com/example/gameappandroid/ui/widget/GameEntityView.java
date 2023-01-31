package com.example.gameappandroid.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.gameutils.GameUtils;
import com.example.gameappandroid.ui.activity.MainActivity;
import com.haiprj.base.interfaces.EntityListener;

@SuppressLint("ViewConstructor")
public class GameEntityView extends FrameLayout {

    private GameView topEntity, bottomEntity;


    private final EntityManager entityManager;
    private EntityListener listener;

    private float entitySpace;

    public GameEntityView(@NonNull Context context, EntityManager entityManager) {
        super(context);
        this.entityManager = entityManager;
        init();
    }

    public GameEntityView(@NonNull Context context, @Nullable AttributeSet attrs, EntityManager entityManager) {
        super(context, attrs);
        this.entityManager = entityManager;
        init();
    }

    public GameEntityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, EntityManager entityManager) {
        super(context, attrs, defStyleAttr);
        this.entityManager = entityManager;
        init();
    }

    public GameEntityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, EntityManager entityManager) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.entityManager = entityManager;
        init();
    }

    public void setListener(EntityListener listener) {
        this.listener = listener;
    }

    public void setEntitySpace(float entitySpace) {
        this.entitySpace = entitySpace;
        float topHeight = entityManager.getY() - entitySpace / 2f;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        EntityManager entityT = new EntityManager("1", R.drawable.wall_entity_top, params.width, params.height, 0, -(params.height - topHeight));
        entityT.worldX = entityT.getX();
        entityT.worldY = entityT.getY();
        topEntity = new GameView(getContext(), entityT);
        EntityManager entityB = new EntityManager("2", R.drawable.wall_entity, params.width, params.height, 0, entityManager.getY() + entitySpace / 2);
        entityB.worldX = entityB.getX();
        entityB.worldY = entityB.getY();
        bottomEntity = new GameView(getContext(), entityB);
        this.addView(topEntity, 0);
        this.addView(bottomEntity, 1);
    }

    private void init(){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(entityManager.getWidth(), entityManager.getHeight());
        this.setLayoutParams(params);
        this.setX(entityManager.getX());
        this.setY(0);
        this.setBackgroundColor(Color.TRANSPARENT);

    }


    public boolean checkIntersect(PlayerManager playerManager){
        RectF topRect = new RectF(this.getX(), this.getY(), topEntity.getWidth(), topEntity.getHeight());
        RectF bottomRect = new RectF(this.getX(), this.getY() + this.getHeight() - bottomEntity.getHeight(), bottomEntity.getWidth(), bottomEntity.getHeight());
        return RectF.intersects(playerManager.getRectF(), topRect) || RectF.intersects(playerManager.getRectF(), bottomRect);
    }

    public RectF getChildRectF(int index){
        if (index > 1){
            return new RectF();
        }
        RectF rectF = ((GameView) this.getChildAt(index)).getEntity().getRectF();
        float w = rectF.width();
        float h = rectF.height();
        rectF.left += this.getX();
        rectF.top += this.getY();
        rectF.right = rectF.left + w;
        rectF.bottom = rectF.top + h;
        return rectF;
    }

    public void update(PlayerManager playerManager){
        entityManager.setX(entityManager.worldX - playerManager.worldX + playerManager.getX());
        //entityManager.setY(entityManager.worldY - playerManager.worldY + playerManager.getY());
        invalidate();
        if (entityManager.getX() <= -entityManager.getWidth() * 2){
            remove();
        }
    }

    private void remove() {
        listener.onRemove();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        (((Activity) getContext())).runOnUiThread(() -> {
            this.setX(entityManager.getX());
            this.setY(entityManager.getY() - entityManager.getHeight() / 2f);
        });
    }


}
