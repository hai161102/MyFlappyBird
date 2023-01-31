package com.example.gameappandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.gameappandroid.ui.dialog.DeathDialog;
import com.haiprj.base.view.BaseActivity;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.ActivityGameBinding;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.interfaces.GameListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Objects;

public class GameActivity extends BaseActivity<ActivityGameBinding> {

    Dialog dialog;
    public static void start(Context context) {
        Intent starter = new Intent(context, GameActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        binding.gameFrame.start();
    }

    @Override
    protected void addEvent() {
        binding.gameFrame.setGameListener(new GameListener() {
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
                        finish();
                    }
                    if (Objects.equals(key, "replay")){

                        binding.gameFrame.resumePlay();
                    }
                }, playerManager).showDialog();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.gameFrame.destroy();
    }
}
