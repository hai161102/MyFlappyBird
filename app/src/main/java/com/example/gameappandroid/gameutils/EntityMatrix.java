package com.example.gameappandroid.gameutils;

import com.example.gameappandroid.gamemodel.EntityManager;

public class EntityMatrix {
    private final int col;
    private final int row;
    private final EntityManager[][] entityManagers;

    public EntityMatrix(int col, int row) {
        this.col = col;
        this.row = row;
        entityManagers = new EntityManager[col][row];
    }

    public EntityMatrix(int levels) {
        this.col = this.row = levels;
        entityManagers = new EntityManager[col][row];
    }

    public EntityManager[][] getEntityManagers() {
        return entityManagers;
    }

    public EntityManager getEntityManagerAt(int x, int y) {
        return entityManagers[x][y];
    }
}
