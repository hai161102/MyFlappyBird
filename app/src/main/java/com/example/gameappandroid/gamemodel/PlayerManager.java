package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.gameappandroid.R;
import com.example.gameappandroid.interfaces.PlayerListener;
import com.haiprj.base.widget.BaseEntity;

public class PlayerManager extends BaseEntity {
    public boolean isJump = false;
    private float playerSpeed;
    private float speedDown;
    public boolean canDown = true, canUp = false, canLeft = false, canRight = true;

    private MediaPlayer mediaPlayer;
    private MediaPlayer deathSound;

    private PlayerListener playerListener;

    public int score = 0;

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
            this.y += this.speedDown;
        }
        if (canUp){
            //this.worldY -= this.playerSpeed;
            this.y -= this.speedDown;
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
