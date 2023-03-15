package com.example.gameappandroid.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.gameappandroid.Const;
import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.Cloud;
import com.example.gameappandroid.gamemodel.Column;
import com.example.gameappandroid.gamemodel.DoubleEntity;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.ui.activity.MainActivity;
import com.haiprj.base.utils.GameRandom;
import com.haiprj.base.utils.GameSharePreference;
import com.haiprj.base.utils.GameUtils;
import com.haiprj.base.widget.BaseGameSurface;

public class GameSurface extends BaseGameSurface {

    private PlayerManager playerManager;
    private Bitmap bgBitmap;

    Column column;

    Cloud cloud;
    private GameRandom random;

    public GameSurface(Context context) {
        super(context);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void init() {
        super.init();
        random = new GameRandom();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView(SurfaceHolder surfaceHolder) {
        initBackground();
        initPlayer();
        initEntity();
        cloud = new Cloud(getContext(), getWidth(), getHeight(), playerManager);
    }

    private void initEntity() {
        column = new Column(getContext(), playerManager, getWidth(), getHeight());
    }

    @Override
    protected void update() {
        this.playerManager.update();
        if (playerManager.worldX / 1000 > 0) {
            playerManager.score = (int) (playerManager.worldX / 1000);
        }
    }


    @Override
    protected void gameDraw(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        playerManager.draw(canvas);
        column.draw(canvas);
        cloud.draw(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initPlayer() {
        float percent = 512f / 449f;
        playerManager = new PlayerManager(
                getContext(),
                "My Bird",
                new int[] {
                        R.drawable.wukong_1,
                        R.drawable.wukong_2,
                        R.drawable.wukong_3,
                },
                GameUtils.getDp(getContext(), (int) (250 * percent)),
                GameUtils.getDp(getContext(), 250),
                getWidth() / 2f,
                getHeight() / 2f,
                GameSharePreference.getInstance().getFloat(Const.PLAYER_SPEED, 6f)
        );
        playerManager.setSpeedDown(8f);
        playerManager.worldX = 0f;
        playerManager.worldY = MainActivity.screenHeight / 2f;
        playerManager.setMediaPlayer(getContext());
    }


    private void initBackground() {
        bgBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bg_flapy_bird);
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, getWidth(), getHeight(), true);
    }

    @Override
    protected void onTouchDown(View view) {
        super.onTouchDown(view);
        playerManager.canUp = true;
        playerManager.canDown = false;
//        if (isResume){
//            isGameOver = false;
//            isResume = false;
//        }
    }

    @Override
    protected void onTouchMove(View view) {
        super.onTouchMove(view);
    }

    @Override
    protected void onTouchUp(View view) {
        super.onTouchUp(view);
        playerManager.canUp = false;
        playerManager.canDown = true;
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
        fixRect.right = this.getWidth();
        fixRect.top = getHeight() / 3;
        fixRect.bottom = getHeight() - fixRect.top;

        float range = fixRect.bottom - fixRect.top;
        float numberRand = random.nextInt((int) range);


        return numberRand + fixRect.top;
    }
}
