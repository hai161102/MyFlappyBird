package com.example.gameappandroid.gamemodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.haiprj.base.widget.BaseEntity;

public class EntityManager extends BaseEntity {


    private Context context;
    private Bitmap[] bitmaps;

    public EntityManager(Context context, String name, int[] imageId, int width, int height, float x, float y) {
        super(name, imageId, width, height, x, y);
        this.context = context;
        bitmaps = new Bitmap[imageId.length];
        for (int i = 0; i < imageId.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(context.getResources(), imageId[i]);
            bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i], this.width, this.height, true);
        }
    }

    public EntityManager() {
    }

    public Bitmap getBitmap() {
        return bitmaps[0];
    }
}
