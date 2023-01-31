package com.example.gameappandroid.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.haiprj.base.widget.BaseEntity;
import com.haiprj.base.widget.BaseEntityView;

@SuppressLint("ViewConstructor")
public class GameBigEntity extends BaseEntityView {



    public enum GameViewGravity {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private GameView topView, bottomView;
    private float pointEmpty;
    private float space;

    public GameBigEntity(@NonNull Context context, BaseEntity baseEntity, float pointEmpty, float space) {
        super(context, baseEntity);
        this.pointEmpty = pointEmpty;
        this.space = space;
        initView();
    }

    public GameBigEntity(@NonNull Context context, @Nullable AttributeSet attrs, BaseEntity baseEntity, float pointEmpty, float space) {
        super(context, attrs, baseEntity);
        this.pointEmpty = pointEmpty;
        this.space = space;
        initView();
    }

    public GameBigEntity(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, BaseEntity baseEntity, float pointEmpty, float space) {
        super(context, attrs, defStyleAttr, baseEntity);
        this.pointEmpty = pointEmpty;
        this.space = space;
        initView();
    }

    public GameBigEntity(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, BaseEntity baseEntity, float pointEmpty, float space) {
        super(context, attrs, defStyleAttr, defStyleRes, baseEntity);
        this.pointEmpty = pointEmpty;
        this.space = space;
        initView();
    }

    @Override
    protected void init() {
        LayoutParams layoutParams = new LayoutParams(baseEntity.getWidth(), baseEntity.getHeight());
        this.setLayoutParams(layoutParams);
        super.init();
    }

    private void initView(){
        topView = new GameView(getContext(), getEntity(GameViewGravity.TOP));
        topView.setRotation(180f);
        bottomView = new GameView(getContext(), getEntity(GameViewGravity.BOTTOM));
        this.addView(topView, 0);
        this.addView(bottomView, 1);
        this.setBackgroundColor(Color.TRANSPARENT);
    }
    private EntityManager getEntity(GameViewGravity gravity) {
        float x = 0;
        float y = 0;
        switch (gravity) {
            case TOP:
                y = - baseEntity.getHeight() + pointEmpty - space / 2f;
                return new EntityManager("top", R.drawable.cot,baseEntity.getWidth(), baseEntity.getHeight(), x, y);
            case BOTTOM:
                y = pointEmpty + space / 2f;

                return new EntityManager("bottom", R.drawable.cot,baseEntity.getWidth(), baseEntity.getHeight(), x, y);
            default:
                return null;
        }
    }

    @Override
    public void update(BaseEntity entity) {

        baseEntity.setX(baseEntity.worldX - entity.worldX + entity.getX());
        //baseEntity.setY(baseEntity.worldY - entity.worldY + entity.getY());
        super.update(entity);
        if (baseEntity.getX() <= -baseEntity.getWidth() * 2){
            remove();
        }
    }

    @Override
    protected void drawUI() {
        this.setX(baseEntity.getX());
        this.setY(baseEntity.getY());
    }

    private void remove() {
        entityListener.onRemove();
    }
    public RectF getChildRectF(int index) {
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
}
