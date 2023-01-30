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
import com.haiprj.base.widget.BaseGameFrame;

import java.io.IOException;
import java.util.Random;

@SuppressWarnings("ALL")
public class GameFrame extends BaseGameFrame {

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

    private MediaPlayer deathSound;

    public GameFrame(@NonNull Context context) {
        super(context);
    }

    public GameFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int entityHeight;
    private int entityWidth;



    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void init(){
        super.init();
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
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void update() {
        super.update();
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
        gameListener.onOver(playerManager);
        playerManager.stopSound();
    }

    private void gameWin() {
        gameListener.onWin(playerManager);
    }

    @Override
    protected void onTouchDown(View view) {
        super.onTouchDown(view);
        playerManager.canUp = true;
        playerManager.canDown = false;
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

    private int getRandomPostionSpawn(int lenght, int startPoint){
        int pos = random.nextInt(lenght - startPoint) + startPoint;
        return pos;
    }

    @Override
    public void destroy(){
        super.destroy();
        this.playerManager.releaseSound();
    }
}
