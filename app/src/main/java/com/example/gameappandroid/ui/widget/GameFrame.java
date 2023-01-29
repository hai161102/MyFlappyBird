package com.example.gameappandroid.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.gameutils.GameMatrix;
import com.example.gameappandroid.gameutils.GameUtils;
import com.example.gameappandroid.interfaces.GameListener;
import com.example.gameappandroid.interfaces.PlayerListener;
import com.example.gameappandroid.ui.activity.MainActivity;

import java.io.IOException;
import java.util.Random;

@SuppressWarnings("ALL")
public class GameFrame extends FrameLayout implements Runnable, View.OnTouchListener {

    public Thread gameThread;
    private final int FPS = 60;

    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    public static final float MAX_WIDTH = 2000;

    private GamePlayerView ballImageView;
    private PlayerManager playerManager;
    private final DisplayMetrics displayMetrics = new DisplayMetrics();
    private GameEntityView[] gameEntityViews;

    private GameListener gameListener;

    private float beforeEntityX = 0;

    private Random random;
    float countLenght = 0;

    private boolean isGameOver = false;

    private MediaPlayer deathSound;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isGameOver){
                update();
                //updateViewLayout();
                GameFrame.this.invalidate();
                GameFrame.this.postDelayed(runnable, 100/FPS);
            }

        }
    };

    private void updateViewLayout() {
        if (getChildCount() > 0)
            for (int i = 0; i < getChildCount(); i++) {
                this.updateViewLayout(this.getChildAt(i), this.getChildAt(i).getLayoutParams());
            }
    }

    private int entityHeight;
    private int entityWidth;

    public GameFrame(@NonNull Context context) {
        super(context);
        init();
    }

    public GameFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GameFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        deathSound = MediaPlayer.create(getContext(), R.raw.death_sound);

        deathSound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
            }
        });
        deathSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                gameOver();
            }
        });
        random = new Random();
        setLayout();
        initPlayer();
        initBackground();
        //initOtherBackground();
        ballImageView = new GamePlayerView(getContext(), playerManager);
        this.addView(ballImageView, 0);
        this.setOnTouchListener(this);
    }

    private void setLayout() {

        entityWidth = (int) GameUtils.pxFromDp(getContext(), 32);
        entityHeight = (int) GameUtils.pxFromDp(getContext(), 32);

        LayoutParams layoutParams = new LayoutParams(MainActivity.screenWidth, MainActivity.screenHeight);
        this.setLayoutParams(layoutParams);
    }

    private void initBackground() {
        gameEntityViews = new GameEntityView[200];

        for (int i = 0; i < gameEntityViews.length; i++) {

            gameEntityViews[i] = getNewEntity(i);
            this.addView(gameEntityViews[i]);
        }
    }

    private GameEntityView getNewEntity(int index){
        float plusSpace = 0;
        if (plusSpace == 0){
            plusSpace += MainActivity.screenWidth;
        }
        if (index > 0){
            plusSpace = gameEntityViews[index - 1].getX() + entityWidth * 2;
        }
        EntityManager entityManager = new EntityManager("", R.drawable.wall, entityWidth, MainActivity.screenHeight, plusSpace, getHeightSpawnEntity());
        entityManager.worldX = entityManager.getX();
        entityManager.worldY = entityManager.getY();
        GameEntityView gameEntityView = new GameEntityView(getContext(), entityManager);
        gameEntityView.setListener(new GameImageView.EntityListener() {
            @Override
            public void onRemove() {
                gameEntityViews[0] = null;
                GameFrame.this.removeView(gameEntityView);
                for (int j = 0; j < gameEntityViews.length - 1; j++){
                    gameEntityViews[j] = gameEntityViews[j+1];
                }
                gameEntityViews[199] = getNewEntity(199);
            }
        });
        return gameEntityView;
    }

    private void initPlayer() {
        float percent = 817 / 577;
        playerManager = new PlayerManager();
        playerManager.setX(MainActivity.screenWidth / 2f);
        playerManager.setY(MainActivity.screenHeight / 2f);
        playerManager.setWidth((int) GameUtils.getDp(getContext(), (int) (24 * percent)));
        playerManager.setHeight((int) GameUtils.getDp(getContext(), 24));
        playerManager.setPlayerSpeed(4f);
        playerManager.setName("Ball");
        playerManager.setImageId(R.drawable.bird);
        playerManager.worldX = 0f;
        playerManager.worldY = MainActivity.screenHeight / 2;
        playerManager.setMediaPlayer(getContext());
    }


    public void start(){
//        gameThread = new Thread(this);
//        gameThread.start();
        this.post(runnable);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000f / FPS;
        double nextTime = System.nanoTime() + drawInterval;

        while (this.gameThread != null) {
            ((Activity) getContext()).runOnUiThread(runnable);

            try {
                double remainTime = nextTime - System.nanoTime();
                remainTime /= 1000000;
                if (remainTime < 0) {
                    remainTime = 0;
                }

                //noinspection BusyWait
                Thread.sleep((long) remainTime);
                nextTime += drawInterval;

            } catch (Exception ignored) {

            }

        }
    }

    public void update() {

        this.playerManager.update();
        this.ballImageView.update();
        for (int i = 0; i  < gameEntityViews.length; i++){
            gameEntityViews[i].update(playerManager);
            if (RectF.intersects(playerManager.getRectF(), gameEntityViews[i].getChildRectF(0) ) || RectF.intersects(playerManager.getRectF(), gameEntityViews[i].getChildRectF(1) )){
                deathSound.start();
            }
        }

        if (playerManager.getY() >= MainActivity.screenHeight || playerManager.getY() <= 0){
            gameOver();
        }


    }



    private float getHeightSpawnEntity(){

        final RectF fixRect = new RectF();
        fixRect.left = 0f;
        fixRect.right = MainActivity.screenWidth;
        fixRect.top = MainActivity.screenHeight / 3;
        fixRect.bottom = MainActivity.screenHeight - fixRect.top;

        float range = fixRect.bottom - fixRect.top;
        float numberRand = random.nextInt((int) range);


        return numberRand + fixRect.top;
    }

    private void gameOver() {
        isGameOver = true;
        gameListener.onOver();
        playerManager.stopSound();
    }

    private void gameWin() {
        gameListener.onWin(playerManager);
        gameThread.interrupt();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                playerManager.canUp = true;
                playerManager.canDown = false;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                playerManager.canUp = false;
                playerManager.canDown = true;
                break;
        }
        return true;
    }

    private int getRandomPostionSpawn(int lenght, int startPoint){
        int pos = random.nextInt(lenght - startPoint) + startPoint;
        return pos;
    }

    public void destroy(){
        this.playerManager.releaseSound();
        this.gameThread.currentThread().interrupt();
    }
}
