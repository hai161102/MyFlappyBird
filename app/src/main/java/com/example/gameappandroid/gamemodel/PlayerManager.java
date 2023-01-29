package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioRouting;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.gameappandroid.R;
import com.example.gameappandroid.interfaces.PlayerListener;
import com.haiprj.base.BaseEntity;

import java.io.IOException;

public class PlayerManager extends BaseEntity {
    public boolean isJump = false;
    private float playerSpeed;

    public boolean canDown = true, canUp = false, canLeft = false, canRight = true;

    private MediaPlayer mediaPlayer;
    private MediaPlayer deathSound;

    private PlayerListener playerListener;

    public PlayerManager(String name, int imageId, int width, int height, float x, float y, float playerSpeed) {
        super(name, imageId, width, height, x, y);
        this.playerSpeed = playerSpeed;

    }

    public PlayerManager() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMediaPlayer(Context context) {
        this.mediaPlayer = MediaPlayer.create(context, R.raw.fly_sound);
        this.deathSound = MediaPlayer.create(context, R.raw.death_sound);
        this.deathSound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.pause();
            }
        });
        this.deathSound.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {
                playerListener.onSoundComplete();
            }
        });
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
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
            this.y += this.playerSpeed;
        }
        if (canUp){
            //this.worldY -= this.playerSpeed;
            this.y -= this.playerSpeed;
            playFlySound();

        }

        else {
            resetSound();
        }

    }

    public void playFlySound() {
        if (mediaPlayer != null) mediaPlayer.start();
    }

    public void playDeathSound(PlayerListener playerListener){
        this.playerListener = playerListener;
        this.deathSound.start();

    }
    public void resetSound(){
        if (mediaPlayer != null) mediaPlayer.seekTo(0);
    }

    public void stopSound(){
        this.mediaPlayer.stop();
    }

    public void releaseSound(){
        this.mediaPlayer.release();
        this.deathSound.release();
    }


}
