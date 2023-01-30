package com.example.gameappandroid.ui.dialog;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.DialogFinishBinding;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.haiprj.base.view.BaseDialog;

public class DeathDialog extends BaseDialog<DialogFinishBinding> {

    private PlayerManager playerManager;

    public DeathDialog(@NonNull Context context, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager) {
        super(context, activity, onActionDialogCallback);
        this.playerManager = playerManager;
    }

    public DeathDialog(@NonNull Context context, int themeResId, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager) {
        super(context, themeResId, activity, onActionDialogCallback);
        this.playerManager = playerManager;
    }

    public DeathDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager) {
        super(context, cancelable, cancelListener, activity, onActionDialogCallback);
        this.playerManager = playerManager;
    }


    @Override
    protected void addEvent() {
        binding.okBtn.setOnClickListener(view -> {
            onActionDialogCallback.callback("OnOke");
            dismiss();
        });
    }

    @Override
    protected void initView() {
        binding.playerName.setText(playerManager.getName());
        binding.imageDialog.setImageResource(playerManager.getImageId());
        binding.title.setText(R.string.game_over);
        binding.score.setText("0");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_finish;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        activity.finish();
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        activity.finish();
    }
}
