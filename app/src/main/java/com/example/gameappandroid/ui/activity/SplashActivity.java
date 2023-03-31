package com.example.gameappandroid.ui.activity;

import android.annotation.SuppressLint;

import com.example.gameappandroid.AdCache;
import com.example.gameappandroid.BuildConfig;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.ActivitySplashBinding;
import com.example.gameappandroid.ui.widget.SplashSurface;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.haiprj.android_app_lib.interfaces.AdCallback;
import com.haiprj.android_app_lib.my_admobs.AdmobManager;
import com.haiprj.base.view.BaseActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {


    @Override
    protected void initView() {
        loadInter();
        binding.gameSurface.startThread();
    }

    private void loadInter() {
        AdmobManager.getInstance().loadInterAds(this, BuildConfig.inter_splash, new AdCallback() {
            @Override
            public void onAdFailedToLoad(com.google.android.gms.ads.LoadAdError i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void interCallback(InterstitialAd interstitialAd) {
                super.interCallback(interstitialAd);
                AdCache.getInstance().setSplashInterstitial(interstitialAd);

            }
        });
    }

    @Override
    protected void addEvent() {
        binding.gameSurface.setLineChangeListener(new SplashSurface.LineChangeListener() {
            @Override
            public void onChange(float current, float max) {

            }

            @Override
            public void onMax() {
                binding.gameSurface.stopThread();
                startMain();
            }
        });
    }

    private void startMain() {
        showInter();

    }

    private void showInter() {
        if (AdCache.getInstance().getSplashInterstitial() == null){
            goToMain();
            return;
        }
        AdmobManager.getInstance().showInterstitial(this, AdCache.getInstance().getSplashInterstitial(), new AdCallback() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                goToMain();
            }


        });
    }

    private void goToMain() {
        MainActivity.start(SplashActivity.this);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
