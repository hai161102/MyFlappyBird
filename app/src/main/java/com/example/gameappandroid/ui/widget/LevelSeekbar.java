package com.example.gameappandroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;

public class LevelSeekbar extends androidx.appcompat.widget.AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {

    /**
     * Notification that the progress level has changed. Clients can use the fromUser parameter
     * to distinguish user-initiated changes from those that occurred programmatically.
     *
     * @param seekBar  The SeekBar whose progress has changed
     * @param progress The current progress level. This will be in the range min..max where min
     *                 and max were set by {@link ProgressBar#setMin(int)} and
     *                 {@link ProgressBar#setMax(int)}, respectively. (The default values for
     *                 min is 0 and max is 100.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currentLevel = progress;
        if (listener != null)
            listener.onSeekProgress(progress);
    }

    /**
     * Notification that the user has started a touch gesture. Clients may want to use this
     * to disable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Notification that the user has finished a touch gesture. Clients may want to use this
     * to re-enable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (currentLevel) {
            case 0:
                level = Level.NORMAL;
                break;
            case 1:
                level = Level.MEDIUM;
                break;
            case 2:
                level = Level.HARD;
                break;
            case 3:
                level = Level.VERY_HARD;
                break;
        }
        if (listener != null)
            listener.onStopSeek(level);
    }

    public enum Level {
        NORMAL,
        MEDIUM,
        HARD,
        VERY_HARD;

        public String getName(Context context) {
            switch (this) {
                case NORMAL:
                    return context.getString(R.string.normal);
                case MEDIUM:
                    return context.getString(R.string.level_medium);
                case HARD:
                    return context.getString(R.string.hard);
                case VERY_HARD:
                    return context.getString(R.string.very_hard);
                default:
                    return "null";
            }
        }
    }

    private final int maxLength = 3;
    private int currentLevel = 0;

    private Level level;

    private OnSeek listener;

    public void setListener(OnSeek listener) {
        this.listener = listener;
    }

    public LevelSeekbar(@NonNull Context context) {
        super(context);
        init();
    }

    public LevelSeekbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LevelSeekbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setMax(maxLength);
        this.setOnSeekBarChangeListener(this);
        this.setProgress(currentLevel, true);
    }

    public Level getLevel() {
        return level;
    }

    public interface OnSeek {
        void onSeekProgress(int pos);
        void onStopSeek(Level level);
    }
}
