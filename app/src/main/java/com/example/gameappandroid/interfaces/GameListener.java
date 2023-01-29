package com.example.gameappandroid.interfaces;

import com.example.gameappandroid.gamemodel.PlayerManager;

public interface GameListener {

    void onWin(PlayerManager playerManager);
    void onOver();
}
