package com.example.gameappandroid.ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.gameappandroid.Const;
import com.example.gameappandroid.gamemodel.GameMediaObject;
import com.example.gameappandroid.ui.adapter.GameMenuAdapter;
import com.haiprj.base.enums.MediaEnum;
import com.haiprj.base.models.MediaObject;
import com.haiprj.base.view.BaseActivity;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.ActivityMainBinding;
import com.haiprj.base.view.BaseAdapter;
import com.haiprj.base.utils.GameSharePreference;
import com.haiprj.base.widget.GameMedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private DisplayMetrics displayMetrics;
    private GameMenuAdapter gameMenuAdapter;

    private float gamePlayerSpeed = 6f;
    public static int screenWidth;
    public static int screenHeight;
    @Override
    protected void initView() {
        GameSharePreference.getInstance().init(this);
        gamePlayerSpeed = GameSharePreference.getInstance().getFloat(Const.PLAYER_SPEED, 6f);
        if (GameSharePreference.getInstance().getBoolean(Const.SOUND_CHECK_KEY, false)){
            openSound();
        }
        else {
            closeSound();
        }

        GameMedia.getInstance(this, getListMediaObjects());

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        gameMenuAdapter = new GameMenuAdapter(this, getListMenu());
        binding.layoutMain.rcvMenu.setAdapter(gameMenuAdapter);
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        assert binding.layoutNavigation.speedSeekBar != null;
        binding.layoutNavigation.speedSeekBar.setProgress((int) (gamePlayerSpeed * 10), true);

    }

    @Override
    protected void addEvent() {
        gameMenuAdapter.setOnViewItemClickListener(new BaseAdapter.OnViewItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        GameActivity.start(MainActivity.this);
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        });
        binding.layoutMain.options.setOnClickListener(view -> {
            binding.drawer.openDrawer(binding.navigationView, false);
        });

        binding.layoutNavigation.soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    openSound();
                }
                else {
                    closeSound();
                }
                GameSharePreference.getInstance().setBoolean(Const.SOUND_CHECK_KEY, b);
            }
        });
        binding.layoutNavigation.speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                gamePlayerSpeed = i / 10f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                GameSharePreference.getInstance().setFloat(Const.PLAYER_SPEED, gamePlayerSpeed);
            }
        });
    }

    private void closeSound() {
        GameMedia.getInstance(this).setMute(true);
    }

    private void openSound() {
        GameMedia.getInstance(this).setMute(false);
    }

    private List<String> getListMenu(){

        List<String> list = new ArrayList<>();
        list.add("Start");
        list.add("Exit");

        return list;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private List<MediaObject> getListMediaObjects() {
        List<MediaObject> mediaObjects = new ArrayList<>();

        mediaObjects.add(new GameMediaObject(MediaEnum.FLY_SONG, R.raw.fly_sound));
        mediaObjects.add(new GameMediaObject(MediaEnum.DEATH_SONG, R.raw.death_sound));
        return mediaObjects;
    }
}
