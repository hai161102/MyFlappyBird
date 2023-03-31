package com.example.gameappandroid.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.gameappandroid.Const;
import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.Cloud;
import com.example.gameappandroid.gamemodel.Column;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.interfaces.GameListener;
import com.example.gameappandroid.ui.activity.GameActivity;
import com.example.gameappandroid.ui.activity.MainActivity;
import com.haiprj.base.utils.GameRandom;
import com.haiprj.base.utils.GameSharePreference;
import com.haiprj.base.utils.GameUtils;
import com.haiprj.base.widget.BaseGameSurface;

import java.util.Objects;

public class GameSurface extends BaseGameSurface {

    private static GameSurface instance;
    private PlayerManager playerManager;
    private Bitmap bgBitmap;

    Column column;

    Cloud cloud;

    private boolean isPortrait;
    private GameRandom random;

    private GameListener listener;

    public GameListener getListener() {
        return listener;
    }

    public void setListener(GameListener listener) {
        this.listener = listener;
    }

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
        this.setBackgroundColor(getContext().getColor(R.color.background_color));
        isPortrait = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (isPortrait){
            this.setBackgroundResource(R.drawable.app_bg_portrait);
        }
        else this.setBackgroundResource(R.drawable.app_bg);
        random = new GameRandom();
    }

    @Override
    protected void initView(SurfaceHolder surfaceHolder) {
        reloadData();
    }

    private void reloadData() {
        initBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initPlayer();
        }
        initEntity();
    }

    private void initEntity() {
        column = new Column(getContext(), playerManager, getWidth(), getHeight(), isPortrait);
        cloud = new Cloud(getContext(), getWidth(), getHeight(), playerManager);
    }

    @Override
    protected void update() {
        this.playerManager.update();
        this.column.update();
        if (playerManager.worldX / 1000 > 0) {
            playerManager.score = (int) (playerManager.worldX / 1000);
        }
        Log.d("hasIntersection", "update: " + column.hasIntersection());
        if (column.hasIntersection()) {
            gameOver();
        }
    }

    private void gameOver() {
        isGameOver = true;
        listener.onOver(playerManager);
        playerManager.onGameOver();
        column.onGameOver();
        stopThread();
    }


    @Override
    protected void gameDraw(Canvas canvas) {
//        canvas.drawBitmap(bgBitmap, 0, 0, null);
        playerManager.draw(canvas);
        column.draw(canvas);
        cloud.draw(canvas);
        canvas.drawText(String.format(getContext().getString(R.string.score_text), playerManager.score), 50f, 50f, playerManager.textPain);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initPlayer() {
        float percent = 512f / 449f;
        playerManager = PlayerManager.getInstance(
                getContext(),
                "My Bird",
                new int[] {
                        R.drawable.fly_wukong,
//                        R.drawable.wukong_1_1,
                        R.drawable.fly_wukong_1,
                },
                GameUtils.getDp(getContext(), (int) (300 * percent)),
                GameUtils.getDp(getContext(), 300),
                getWidth() / 2f,
                getHeight() / 4f,
                GameSharePreference.getInstance().getFloat(Const.PLAYER_SPEED, 6f),
                -1
        );
        float speedDown = 0f;
        LevelSeekbar.Level defaultLevel = LevelSeekbar.Level.NORMAL;
        LevelSeekbar.Level level = GameUtils.getFromJson(
                GameSharePreference.getInstance().getString(Const.PLAYER_LEVEL,
                        GameUtils.convertToJson(defaultLevel)),
                LevelSeekbar.Level.class);

        float defaultSpeed = 10f;
        switch (level) {
            case NORMAL:
                speedDown = defaultSpeed;
                break;
            case MEDIUM:
                speedDown = defaultSpeed * 1.2f;
                break;
            case HARD:
                speedDown = defaultSpeed * 1.5f;
                break;
            case VERY_HARD:
                speedDown = defaultSpeed * 1.8f;
        }
        playerManager.setSpeedDown(speedDown);
        playerManager.worldX = 0f;
        playerManager.worldY = MainActivity.screenHeight / 2f;
        playerManager.setMediaPlayer();
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

    public void playAgain() {
        reloadData();
        isGameOver = false;
        gameThread = null;
        postInvalidate();
        TextView playText = ((GameActivity) getContext()).getBinding().playText;
        playText.setVisibility(VISIBLE);
        playText.setOnClickListener(v -> {
            playText.setVisibility(GONE);
            startThread();

        });
    }

    public void resumePlay() {
        if (column.getDieAt() != null) {
            playerManager.setY(column.getDieAt().getGoodResumePlace());
        }
        else {
            playerManager.setY(getHeight() / 4f);
        }
        playerManager.worldY = playerManager.getY();
        isGameOver = false;
        gameThread = null;
        postInvalidate();
        TextView playText = ((GameActivity) getContext()).getBinding().playText;
        playText.setVisibility(VISIBLE);
        playText.setOnClickListener(v -> {
            playText.setVisibility(GONE);
            startThread();
        });
    }

    public void clearAll() {
        instance = null;
        playerManager.releaseSound();
    }
}
