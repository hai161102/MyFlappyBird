package com.example.gameappandroid;

import android.app.Application;
import android.media.MediaPlayer;

import com.haiprj.base.utils.GameSharePreference;

public class MyApplication extends com.haiprj.android_app_lib.MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.game_sound);
//        mediaPlayer.start();
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mediaPlayer.seekTo(0);
//                mediaPlayer.start();
//            }
//        });
    }

    @Override
    protected boolean isPurchased() {
        return false;
    }

    @Override
    protected boolean isShowAdsTest() {
        return true;
    }

    @Override
    public boolean enableAdsResume() {
        return false;
    }

    @Override
    public String getOpenAppAdId() {
        return null;
    }
}
