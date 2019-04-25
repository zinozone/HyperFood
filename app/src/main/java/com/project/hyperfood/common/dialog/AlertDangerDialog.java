package com.project.hyperfood.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.project.hyperfood.R;
import com.project.hyperfood.activity.ReportActivity;
import com.project.hyperfood.databinding.DialogFullScreenBinding;

public class AlertDangerDialog extends DialogFragment {

    public static String TAG = "AlertDangerDialog";
    public static String ALERT_KEY = "alert-key";

    public static final int ALERT_CARBOHYDRATE = 1;
    public static final int ALERT_SODIUM = 2;
    public static final int ALERT_KCAL = 3;

    private Context context;
    private int alert;
    private  ReportActivity.onDialogClick dialogClick;

    public AlertDangerDialog(Context context) {
        this.context = context;
    }

    public void setDialogClick(ReportActivity.onDialogClick dialogClick) {
        this.dialogClick = dialogClick;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        Bundle b = getArguments();
        alert = b.getInt(ALERT_KEY, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DialogFullScreenBinding binding = DialogFullScreenBinding.inflate(layoutInflater, container, false);
        binding.btnDismiss.setOnClickListener(v -> dialogClick.onDismiss());
        binding.btnHowTo.setOnClickListener(v -> dialogClick.onHowToClick());
        switch (alert){
            case ALERT_CARBOHYDRATE :
                binding.tvAlert.setText(context.getString(R.string.danger_carbohydrate));
                break;
            case ALERT_SODIUM :
                binding.tvAlert.setText(context.getString(R.string.danger_sodium));
                break;
            case ALERT_KCAL :
                binding.tvAlert.setText(context.getString(R.string.danger_kcal));
                break;
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
