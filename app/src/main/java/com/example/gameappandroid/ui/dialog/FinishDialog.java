package com.example.gameappandroid.ui.dialog;

import android.content.Context;
import android.content.Intent;

import com.haiprj.base.BaseActivity;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.DialogFinishBinding;
import com.example.gameappandroid.interfaces.OnActionCallback;

public class FinishDialog extends BaseActivity<DialogFinishBinding> {

    private static OnActionCallback callback;

    public static void start(Context context, OnActionCallback onActionCallback) {
        callback = onActionCallback;
        Intent starter = new Intent(context, FinishDialog.class);
        context.startActivity(starter);
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void addEvent() {
        binding.okBtn.setOnClickListener(view -> {
            callback.callback("OnOke");
            finish();
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_finish;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}
