package com.example.gameappandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.gameappandroid.AdCache;
import com.example.gameappandroid.BuildConfig;
import com.example.gameappandroid.R;
import com.example.gameappandroid.ui.dialog.DeathDialog;
import com.example.gameappandroid.ui.widget.GameSurface;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.haiprj.android_app_lib.interfaces.AdCallback;
import com.haiprj.android_app_lib.my_admobs.AdmobManager;
import com.haiprj.base.enums.MediaEnum;
import com.haiprj.base.view.BaseActivity;
import com.example.gameappandroid.databinding.ActivityGameBinding;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.interfaces.GameListener;
import com.haiprj.base.widget.GameMedia;

import java.util.Objects;

public class GameActivity extends BaseActivity<ActivityGameBinding> {

    Dialog dialog;
    private boolean isStart = true;
    private boolean isPlayAgain;
    private boolean isContinue;
    public static void start(Context context) {
        Intent starter = new Intent(context, GameActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        AdmobManager.getInstance().loadBanner(this, BuildConfig.banner_game);
    }

    @Override
    protected void addEvent() {

        binding.playText.setOnClickListener(v -> {
            if (isStart){
                binding.gameSurface.startThread();
                isStart = false;
            }
            v.setVisibility(View.GONE);
        });
        binding.gameSurface.setListener(new GameListener() {
            @Override
            public void onWin(PlayerManager playerManager) {
                DeathDialog.getInstance(GameActivity.this, GameActivity.this, (key, objects) -> {
                    if (Objects.equals(key, "OnOke")){
                        finish();
                    }
                }, playerManager).showDialog();

            }

            @Override
            public void onOver(PlayerManager playerManager) {

                DeathDialog.getInstance(GameActivity.this, GameActivity.this, (key, objects) -> {
                    if (Objects.equals(key, "OnOke")){
                        showInter();
                    }
                    if (Objects.equals(key, "replay")){
                        binding.gameSurface.playAgain();
                    }
                    if (Objects.equals(key, "resume")){
                        binding.gameSurface.resumePlay();
                    }
                }, playerManager).show();
            }
        });
//        GameMedia.getInstance(this).getMediaPlayerAt(MediaEnum.DEATH_SONG).setOnCompletionListener(mediaPlayer -> {
//            binding.gameFrame.gameOver();
//        });
    }

    private void showInter() {
        if (AdCache.getInstance().getGameInterstitial() != null) {
            AdmobManager.getInstance().showInterstitial(this, AdCache.getInstance().getGameInterstitial(), new AdCallback() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }
        else {
            finish();
        }
    }

    public ActivityGameBinding getBinding() {
        return binding;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (AdCache.getInstance().getGameInterstitial() == null) {
            AdmobManager.getInstance().loadInterAds(this, BuildConfig.inter_exit, new AdCallback() {
                @Override
                public void interCallback(InterstitialAd interstitialAd) {
                    super.interCallback(interstitialAd);
                    AdCache.getInstance().setGameInterstitial(interstitialAd);
                }
            });
        }
    }

    @Override
    public void finish() {
        binding.gameSurface.clearAll();
        binding.layout.removeAllViews();
        super.finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
