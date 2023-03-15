package com.example.gameappandroid.gameutils;

import android.graphics.RectF;
import android.util.Log;

public class GameMatrix {

    public final int col;
    public final int row;
    public GameMatrix(int col, int row) {
        this.col = col;
        this.row = row;

        Log.d("Matrix size", "GameMatrix: matrix size col = " + this.col + ", row = " + this.row);
    }

    public RectF getRectF(GameSize gameSize){
        return new RectF(0f, 0f, col * gameSize.width, row * gameSize.height);
    }

    public boolean isSquare(){
        return row == col;
    }


}
