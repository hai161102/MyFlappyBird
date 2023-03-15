package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.gameappandroid.R;
import com.example.gameappandroid.interfaces.PlayerListener;
import com.haiprj.base.enums.MediaEnum;
import com.haiprj.base.models.MediaObject;
import com.haiprj.base.widget.BaseEntity;
import com.haiprj.base.widget.GameMedia;

import java.lang.reflect.Array;
import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public class PlayerManager extends BaseEntity {

    private final float ANi_FPS = 60;
    private final Runnable runnable = new Runnable() {
        @SuppressWarnings("BusyWait")
        @Override
        public void run() {
            double drawInterval = 1000000000f / ANi_FPS;
            double nextTime = System.nanoTime() + drawInterval;
            while (thread != null) {
                double remainTime = nextTime - System.nanoTime();
                remainTime /= 1000000;

                animationCount ++;
                if (animationCount >= bitmaps.length) {
                    animationCount = 0;
                }

                try {
                    if (remainTime < 0) {
                        remainTime = 0;
                    }

                    Thread.sleep((long) remainTime);
                    nextTime += drawInterval;
                } catch (InterruptedException ignored) {
                    thread.interrupt();
                }
            }
        }
    };
    protected Context context;
    protected float playerSpeed;
    protected float speedDown;
    public boolean canDown = true, canUp = false, canLeft = false, canRight = true;
    public int score = 0;

    protected Bitmap[] bitmaps;

    Thread thread;
    protected int animationCount = 0;
    public Bitmap getBitmap() {
        Bitmap b = bitmaps[animationCount];
        if (b == null) {
            b = BitmapFactory.decodeResource(context.getResources(), imageId[animationCount]);
            bitmaps[animationCount] = Bitmap.createScaledBitmap(b, this.getWidth(), this.getHeight(), true);
        }
        return bitmaps[animationCount];
    }


    public PlayerManager(Context context, String name, int[] imageId, int width, int height, float x, float y, float playerSpeed) {
        super(name, imageId, width, height, x, y);
        this.context = context;
        this.playerSpeed = playerSpeed;
        bitmaps = new Bitmap[imageId.length];
        for (int i = 0; i < imageId.length; i++) {
            Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageId[i]);
            bitmaps[i] = Bitmap.createScaledBitmap(b, this.getWidth(), this.getHeight(), true);
        }
        thread = new Thread(runnable);
        thread.start();
    }

    public PlayerManager(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMediaPlayer(Context context) {
        this.context = context;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public float getSpeedDown() {
        return speedDown;
    }

    public void setSpeedDown(float speedDown) {
        this.speedDown = speedDown;
    }

    public void update(){
        if (canRight){
            this.worldX += this.playerSpeed;
        }
        else if (canLeft){
            this.worldX -= this.playerSpeed;
        }

        if (canDown && !canUp){
            //this.worldY += this.playerSpeed;
            this.y += (this.speedDown * 1.5f) ;
//            this.y += ((float) Math.sin(this.x * Math.PI / 2) + 1) * speedDown;
        }
        if (canUp){
            //this.worldY -= this.playerSpeed;
            setY(this.getY() - this.speedDown);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //playFlySound();
            }

        }

        else {
            //resetSound();
        }

    }


    @Override
    public void setY(float y) {
        super.setY(y);

    }

    @Override
    public void setImageId(int[] imageId) {
        super.setImageId(imageId);
        bitmaps = new Bitmap[imageId.length];
        for (int i = 0; i < imageId.length; i++) {
            Bitmap b = BitmapFactory.decodeResource(context.getResources(), imageId[i]);
            bitmaps[i] = Bitmap.createScaledBitmap(b, this.getWidth(), this.getHeight(), true);
        }
    }

    private void onDown() {
        animationCount = 3;
    }

    @SuppressWarnings("BusyWait")
    public void onUp(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                double drawInterval = 1000000000f / 60;
                double nextTime = System.nanoTime() + drawInterval;
                while (canUp) {
                    double remainTime = nextTime - System.nanoTime();
                    remainTime /= 1000000;
                    if (animationCount == 0 || animationCount == 3) {
                        animationCount = 1;
                    }
                    if (animationCount == 1) {
                        animationCount = 2;
                    }
                    if (animationCount == 2) {
                        animationCount = 1;
                    }
                    try {
                        if (remainTime < 0) {
                            remainTime = 0;
                        }
                        Thread.sleep((long) remainTime);
                        nextTime += drawInterval;
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
        thread.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playFlySound() {
        GameMedia.getInstance(context).playSong(MediaEnum.FLY_SONG);
    }
    public void resetSound(){
        GameMedia.getInstance(context).getMediaPlayerAt(MediaEnum.FLY_SONG).seekTo(0);
    }

    public void stopSound(){
        GameMedia.getInstance(context).stopSong(MediaEnum.FLY_SONG);
    }

    public void releaseSound(){
        GameMedia.getInstance(context).releaseSong(MediaEnum.FLY_SONG);
    }

    public Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, this.width, this.height, matrix, true);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmaps[animationCount], getX(), getY(), null);
    }

}
