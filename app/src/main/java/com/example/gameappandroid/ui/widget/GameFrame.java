package com.example.gameappandroid.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.Const;
import com.example.gameappandroid.R;
import com.example.gameappandroid.gamemodel.EntityManager;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.interfaces.GameListener;
import com.example.gameappandroid.ui.activity.MainActivity;
import com.haiprj.base.interfaces.EntityListener;
import com.haiprj.base.utils.GameSharePreference;
import com.haiprj.base.utils.GameUtils;
import com.haiprj.base.widget.BaseGameFrame;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Random;

@SuppressWarnings("ALL")
public class GameFrame extends BaseGameFrame {

    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    public static final float MAX_WIDTH = 2000;

    private GamePlayerView ballImageView;
    private PlayerManager playerManager;
    private final DisplayMetrics displayMetrics = new DisplayMetrics();
    private GameBigEntity[] gameEntityViews;

    private GameListener gameListener;

    private float beforeEntityX = 0;

    private Random random;
    float countLenght = 0;

    private MediaPlayer deathSound;

    private GameTextView textView;

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


    private int numberProgressCount = 0;


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
        setupScoreView();
    }

    private void setLayout() {

        entityWidth = MainActivity.screenWidth / 15;
        entityHeight = (int) GameUtils.pxFromDp(getContext(), 32);

        LayoutParams layoutParams = new LayoutParams(MainActivity.screenWidth, MainActivity.screenHeight);
        this.setLayoutParams(layoutParams);
    }

    private void initBackground() {
        gameEntityViews = new GameBigEntity[100];

        for (int i = 0; i < gameEntityViews.length; i++) {

            gameEntityViews[i] = getNewEntity(i);
            this.addView(gameEntityViews[i]);
        }
    }

    private GameBigEntity getNewEntity(int index){
        float plusSpace = 0;
        if (plusSpace == 0){
            plusSpace += MainActivity.screenWidth;
        }
        if (index > 0){
            plusSpace = gameEntityViews[index - 1].getX() + entityWidth * 2.5f;
        }
        EntityManager entityManager = new EntityManager("", R.drawable.wall, entityWidth, MainActivity.screenHeight, plusSpace, 0);
        entityManager.worldX = entityManager.getX();
        entityManager.worldY = entityManager.getY();
        float point = getHeightSpawnEntity();
        Log.d("POINT_RANDOM", "getNewEntity: " + point);
        float space = entityHeight * 3;
        GameBigEntity gameEntityView = new GameBigEntity(getContext(), entityManager, point, space - getMinusSpace(space));
        gameEntityView.setEntityListener(new EntityListener() {
            @Override
            public void onRemove() {
                gameEntityViews[0] = null;
                GameFrame.this.removeView(gameEntityView);
                for (int j = 0; j < gameEntityViews.length - 1; j++){
                    gameEntityViews[j] = gameEntityViews[j+1];
                }
                gameEntityViews[gameEntityViews.length - 1] = getNewEntity(gameEntityViews.length - 1);
            }
        });
        return gameEntityView;
    }

    private void initPlayer() {
        float percent = 817 / 577;
        playerManager = new PlayerManager();
        playerManager.setX(MainActivity.screenWidth / 2f);
        playerManager.setY(MainActivity.screenHeight / 2f);
        playerManager.setWidth((int) GameUtils.getDp(getContext(), (int) (180 * percent)));
        playerManager.setHeight((int) GameUtils.getDp(getContext(), 180));
        playerManager.setPlayerSpeed(GameSharePreference.getInstance().getFloat(Const.PLAYER_SPEED, 6f));
        playerManager.setSpeedDown(8f);
        playerManager.setName("My Bird");
        playerManager.setImageId(R.drawable.bird);
        playerManager.worldX = 0f;
        playerManager.worldY = MainActivity.screenHeight / 2;
        playerManager.setMediaPlayer(getContext());
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private GameBigEntity entityOnDeath;
    @Override
    protected void update() {
        super.update();
        this.playerManager.update();
        this.ballImageView.update();
        if (playerManager.worldX / 1000 > 0) {
            playerManager.score = (int) (playerManager.worldX / 1000);
            textView.setText(String.format(getContext().getString(R.string.dialog_score), playerManager.score));
        }
        for (int i = 0; i  < gameEntityViews.length; i++){
            gameEntityViews[i].update(playerManager);
            if (RectF.intersects(playerManager.getRectF(), gameEntityViews[i].getChildRectF(0) )
                    || RectF.intersects(playerManager.getRectF(), gameEntityViews[i].getChildRectF(1) )
            ){
                entityOnDeath = gameEntityViews[i];
                gameOver();
            }
        }
        if (playerManager.getY() >= MainActivity.screenHeight || playerManager.getY() <= 0){
            gameOver();
        }



    }


    public void playAgain(){
        reset();
    }

    public void resumePlay(){

        setupPlayerResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isGameOver = false;
            }
        }, 3000);
    }

    private void setupPlayerResume() {
        if (entityOnDeath == null){
            playerManager.setY(MainActivity.screenHeight / 2 - playerManager.getHeight() / 2);
        }
        else playerManager.setY(entityOnDeath.getPointEmpty() - playerManager.getHeight() / 2);
        ballImageView.update();

    }

    private void reset(){
        isGameOver = false;
        this.removeAllViews();
        initPlayer();
        initBackground();
        ballImageView = new GamePlayerView(getContext(), playerManager);
        this.addView(ballImageView, 0);
        setupScoreView();
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
        if (playerManager.score > GameSharePreference.getInstance().getInt(Const.HIGHEST_SCORE_KEY, 0)){
            GameSharePreference.getInstance().setInt(Const.HIGHEST_SCORE_KEY, playerManager.score);
        }
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

    public float getMinusSpace (float space){
        float oOT = space / 10f;

        return random.nextInt((int) (oOT * 2));
    }

    public void setupScoreView(){
        textView = new GameTextView(getContext());
        textView.setText(String.format(getContext().getString(R.string.dialog_score), playerManager.score));
        textView.setBackgroundResource(R.drawable.shape_button);
        textView.setTextColor(R.color.app_38);
        textView.setTextSize(GameUtils.getDp(getContext(), 24));
        textView.setX(0);
        textView.setY(0);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(12, 12, 12, 12);
        this.addView(textView);
    }
}
