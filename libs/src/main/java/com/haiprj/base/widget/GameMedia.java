package com.haiprj.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.haiprj.base.enums.MediaEnum;
import com.haiprj.base.models.MediaObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GameMedia {

    private boolean isMute;
    private final Context context;
    public final List<MediaPlayer> mediaPlayers = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static GameMedia instance;
    private final List<MediaObject> listSource = new ArrayList<>();

    private GameMedia(Context context) {
        this.context = context;
    }

    private GameMedia(Context context, List<MediaObject> list) {
        this.context = context;
        addSources(list);
    }

    public static GameMedia getInstance(Context context){
        if (instance == null)
            instance = new GameMedia(context);
        return instance;
    }

    public static GameMedia getInstance(Context context, List<MediaObject> list){
        if (instance == null)
            instance = new GameMedia(context, list);
        return instance;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public void addSource(MediaObject mediaObject) {
        if (listSource.contains(mediaObject)) return;
        listSource.add(mediaObject);
        addMedia(mediaObject);
    }

    private void addMedia(MediaObject mediaObject) {

        MediaPlayer mediaPlayer = MediaPlayer.create(context, mediaObject.rawId);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayers.add(mediaPlayer);

    }
    private void addMedia(MediaObject mediaObject, int index) {

        MediaPlayer mediaPlayer = MediaPlayer.create(context, mediaObject.rawId);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayers.add(index, mediaPlayer);
    }
    public void addSources(List<MediaObject> listSource){
        if (new HashSet<>(this.listSource).containsAll(listSource)) return;
        this.listSource.addAll(listSource);
        listSource.forEach(this::addMedia);
    }

    public void addSources(MediaObject[] listSource){
        if (new HashSet<>(this.listSource).containsAll(Arrays.asList(listSource))) return;;
        this.listSource.addAll(Arrays.asList(listSource));
        Arrays.asList(listSource).forEach(this::addMedia);
    }

    public void playSong(MediaEnum songID) {
        if (isMute) return;
        MediaObject mediaObject = getMediaByEnum(songID);
        int index = this.listSource.indexOf(mediaObject);
        if (mediaPlayers.get(index) == null) {
            assert mediaObject != null;
            addMedia(mediaObject, index);
        }
        this.mediaPlayers.get(index).start();
    }

    public void resetSong(MediaEnum songID) {
        if (isMute) return;
        int index = this.listSource.indexOf(getMediaByEnum(songID));
        this.mediaPlayers.get(index).reset();
    }

    public void resetSong(MediaEnum songID, boolean toZero) {
        if (isMute) return;
        int index = this.listSource.indexOf(getMediaByEnum(songID));
        if (toZero)
            this.mediaPlayers.get(index).seekTo(0);
        else resetSong(songID);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void resetSong(MediaEnum songID, boolean toZero, int mode) {
        if (isMute) return;
        int index = this.listSource.indexOf(getMediaByEnum(songID));
        if (toZero)
            this.mediaPlayers.get(index).seekTo(0, mode);
        else resetSong(songID);
    }

    public void releaseSong (MediaEnum id) {
        if (isMute) return;
        int index = this.listSource.indexOf(getMediaByEnum(id));
        this.mediaPlayers.get(index).release();
    }

    public void stopSong (MediaEnum id) {
        if (isMute) return;
        getMediaPlayerAt(id).stop();
    }

    public MediaPlayer getMediaPlayerAt(int index){
        return this.mediaPlayers.get(index);
    }

    public MediaPlayer getMediaPlayerAt(MediaEnum mediaEnum) {
        return this.mediaPlayers.get(this.listSource.indexOf(getMediaByEnum(mediaEnum)));
    }
    private MediaObject getMediaByEnum(MediaEnum songID) {
        for (int i = 0; i < listSource.size(); i++) {
            if (listSource.get(i).mediaEnum == songID){
                return listSource.get(i);
            }
        }
        return null;
    }

}
