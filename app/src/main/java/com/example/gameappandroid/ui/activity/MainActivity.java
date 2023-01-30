package com.example.gameappandroid.ui.activity;

import android.util.DisplayMetrics;
import android.widget.CompoundButton;

import com.example.gameappandroid.Const;
import com.example.gameappandroid.ui.adapter.GameMenuAdapter;
import com.haiprj.base.view.BaseActivity;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.ActivityMainBinding;
import com.haiprj.base.view.BaseAdapter;
import com.haiprj.base.utils.GameSharePreference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private DisplayMetrics displayMetrics;
    private GameMenuAdapter gameMenuAdapter;

    public static int screenWidth;
    public static int screenHeight;
    @Override
    protected void initView() {
        GameSharePreference.getInstance().init(this);
        if (GameSharePreference.getInstance().getBoolean(Const.SOUND_CHECK_KEY, false)){
            openSound();
        }
        else {
            closeSound();
        }
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        gameMenuAdapter = new GameMenuAdapter(this, getListMenu());
        binding.layoutMain.rcvMenu.setAdapter(gameMenuAdapter);

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
            binding.drawer.openDrawer(binding.navigationView, true);
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
    }

    private void closeSound() {
    }

    private void openSound() {
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
}
