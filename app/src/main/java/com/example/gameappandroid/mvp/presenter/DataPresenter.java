package com.example.gameappandroid.mvp.presenter;

import com.example.gameappandroid.mvp.model.DataModel;
import com.example.gameappandroid.mvp.model.DataResult;
import com.example.gameappandroid.mvp.view.ViewResult;

public class DataPresenter implements DataResult {

    private ViewResult viewResult;
    private DataModel dataModel;
    public DataPresenter(ViewResult viewResult) {
        this.viewResult = viewResult;
        dataModel = new DataModel(this);
    }

    @Override
    public void onDataResultSuccess(String key, Object... objects) {
        viewResult.onViewAvailable(key, objects);
    }

    @Override
    public void onDataResultFailed(String mess) {
        viewResult.onViewNotAvailable(mess);
    }


}
