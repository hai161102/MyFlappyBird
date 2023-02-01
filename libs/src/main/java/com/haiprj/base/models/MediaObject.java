package com.haiprj.base.models;

import android.media.MediaActionSound;
import android.media.MediaDataSource;
import android.media.MediaPlayer;

import com.haiprj.base.enums.MediaEnum;

import java.io.IOException;

public abstract class MediaObject {
    public MediaEnum mediaEnum;
    public int rawId;

    public MediaObject(MediaEnum mediaEnum,  int rawId) {
        this.mediaEnum = mediaEnum;
        this.rawId = rawId;
    }

    public MediaObject() {
    }

    protected void setData(){

    }
}
