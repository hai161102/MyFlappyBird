package com.example.gameappandroid.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.haiprj.base.BaseActivity;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.ActivityGameBinding;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.example.gameappandroid.interfaces.GameListener;
import com.example.gameappandroid.interfaces.OnActionCallback;
import com.example.gameappandroid.ui.dialog.FinishDialog;

import java.util.Objects;

public class GameActivity extends BaseActivity<ActivityGameBinding> {

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
                FinishDialog.start(GameActivity.this, new OnActionCallback() {
                    @Override
                    public void callback(String key, Object... objects) {
                        if (Objects.equals(key, "OnOke"))
                            finish();
                    }
                });
            }

            @Override
            public void onOver() {
                FinishDialog.start(GameActivity.this, new OnActionCallback() {
                    @Override
                    public void callback(String key, Object... objects) {
                        if (Objects.equals(key, "OnOke"))
                            finish();
                    }
                });
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
