package com.example.gameappandroid.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gameappandroid.Const;
import com.example.gameappandroid.R;
import com.example.gameappandroid.databinding.DialogFinishBinding;
import com.example.gameappandroid.gamemodel.PlayerManager;
import com.haiprj.base.utils.GameSharePreference;
import com.haiprj.base.utils.GameUtils;
import com.haiprj.base.view.BaseDialog;

import java.text.MessageFormat;

public class DeathDialog extends BaseDialog<DialogFinishBinding> {

    @SuppressLint("StaticFieldLeak")
    private static DeathDialog instance;
    private PlayerManager playerManager;

    private DeathDialog(@NonNull Context context, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager) {
        super(context, activity, onActionDialogCallback);
        this.playerManager = playerManager;
    }

    private DeathDialog(@NonNull Context context, int themeResId, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager) {
        super(context, themeResId, activity, onActionDialogCallback);
        this.playerManager = playerManager;
    }

    private DeathDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager) {
        super(context, cancelable, cancelListener, activity, onActionDialogCallback);
        this.playerManager = playerManager;
    }

    public static DeathDialog getInstance(@NonNull Context context, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager){
        if (instance == null){
            instance = new DeathDialog(context, activity, onActionDialogCallback, playerManager);
        }
        return instance;
    }

    public static DeathDialog getInstance(@NonNull Context context, int themeResId, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager){
        if (instance == null){
            instance = new DeathDialog(context, themeResId, activity, onActionDialogCallback, playerManager);
        }
        return instance;
    }

    public static DeathDialog getInstance(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, Activity activity, OnActionDialogCallback onActionDialogCallback, PlayerManager playerManager){
        if (instance == null){
            instance = new DeathDialog(context, cancelable, cancelListener, activity, onActionDialogCallback, playerManager);
        }
        return instance;
    }


    @Override
    protected void addEvent() {
        binding.okBtn.setOnClickListener(view -> {
            onActionDialogCallback.callback("OnOke");
            dismiss();
        });

        binding.replay.setOnClickListener(view -> {
            onActionDialogCallback.callback("replay");
            dismiss();
        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        binding.playerName.setText(MessageFormat.format("{0}: {1}", getContext().getText(R.string.player), playerManager.getName()));
        binding.imageDialog.setImageResource(playerManager.getImageId());
        binding.title.setText(R.string.game_over);
        binding.score.setText(String.format(getContext().getString(R.string.dialog_score), playerManager.score));
        assert binding.highestScore != null;
        binding.highestScore.setText(String.format(getContext().getString(R.string.dialog_highest_score), GameSharePreference.getInstance().getInt(Const.HIGHEST_SCORE_KEY, 0)));
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(GameUtils.getDp(getContext(), 4000), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_finish;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

    @Override
    protected void onCancel() {
        super.onCancel();
    }
}
