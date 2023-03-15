package com.haiprj.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseGameTextureView extends TextureView implements TextureView.SurfaceTextureListener, View.OnTouchListener {


    protected Surface gameSurface;
    protected static final int FPS = 72;
    protected boolean isGameOver = false;
    protected Canvas canvas;

    private final Runnable runnable = new Runnable() {
        @SuppressWarnings("SynchronizeOnNonFinalField")
        @Override
        public void run() {
            if (!isGameOver) {
                update();

            }
            try
            {
                canvas = gameSurface.lockCanvas(null);
                synchronized (gameSurface)
                {
                    getSurfaceTexture().updateTexImage();
                    invalidate();
                }

            } finally
            {
                if (canvas != null)
                {
                    gameSurface.unlockCanvasAndPost(canvas);
                }
            }
            postDelayed(this, 1000 / FPS);
        }
    };

    protected abstract void update();

    /**
     * Creates a new TextureView.
     *
     * @param context The context to associate this view with.
     */
    public BaseGameTextureView(@NonNull Context context) {
        super(context);
    }

    /**
     * Creates a new TextureView.
     *
     * @param context The context to associate this view with.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public BaseGameTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new TextureView.
     *
     * @param context      The context to associate this view with.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     */
    public BaseGameTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Creates a new TextureView.
     *
     * @param context      The context to associate this view with.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @param defStyleRes  A resource identifier of a style resource that
     *                     supplies default values for the view, used only if
     *                     defStyleAttr is 0 or can not be found in the theme. Can be 0
     *                     to not look for defaults.
     */
    public BaseGameTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Invoked when a {@link TextureView}'s SurfaceTexture is ready for use.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The width of the surface
     * @param height  The height of the surface
     */

    protected void init() {
        this.setSurfaceTextureListener(this);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setOnTouchListener(this);
    }

    protected abstract void initView(Surface surface);
    protected abstract void gameDraw(Canvas canvas);
    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        gameSurface = new Surface(surface);
        initView(gameSurface);
        post(runnable);

    }

    /**
     * Invoked when the {@link SurfaceTexture}'s buffers size changed.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The new width of the surface
     * @param height  The new height of the surface
     */
    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
     * If returns true, no rendering should happen inside the surface texture after this method
     * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
     * Most applications should return true.
     *
     * @param surface The surface about to be destroyed
     */
    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {

        return removeCallbacks(runnable);
    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is updated through
     * {@link SurfaceTexture#updateTexImage()}.
     *
     * @param surface The surface just updated
     */
    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
        gameSurface = new Surface(surface);
        gameDraw(canvas);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onTouchDown(v);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(v);
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(v);
                break;
        }
        return true;
    }

    protected void onTouchUp(View view) {

    }

    protected void onTouchMove(View view) {

    }

    protected void onTouchDown(View view) {
    }

}
