package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.gameappandroid.R;
import com.example.gameappandroid.interfaces.PlayerListener;
import com.haiprj.base.enums.MediaEnum;
import com.haiprj.base.models.MediaObject;
import com.haiprj.base.widget.BaseEntity;
import com.haiprj.base.widget.GameMedia;

public class PlayerManager extends BaseEntity {


    private Context context;
    private float playerSpeed;
    private float speedDown;
    public boolean canDown = true, canUp = false, canLeft = false, canRight = true;

    public int score = 0;

    public PlayerManager(String name, int imageId, int width, int height, float x, float y, float playerSpeed) {
        super(name, imageId, width, height, x, y);
        this.playerSpeed = playerSpeed;

    }

    public PlayerManager() {
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
            this.y += this.speedDown;
        }
        if (canUp){
            //this.worldY -= this.playerSpeed;
            this.y -= this.speedDown;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                playFlySound();
            }

        }

        else {
            resetSound();
        }

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


}
