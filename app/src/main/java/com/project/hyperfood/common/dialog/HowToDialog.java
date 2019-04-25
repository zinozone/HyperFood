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
import com.project.hyperfood.databinding.DialogHowToBinding;

import static com.project.hyperfood.common.dialog.AlertDangerDialog.ALERT_CARBOHYDRATE;
import static com.project.hyperfood.common.dialog.AlertDangerDialog.ALERT_KCAL;
import static com.project.hyperfood.common.dialog.AlertDangerDialog.ALERT_KEY;
import static com.project.hyperfood.common.dialog.AlertDangerDialog.ALERT_SODIUM;

public class HowToDialog extends DialogFragment {

    public static String TAG = "HowToDialog";

    private Context context;
    private int alert;

    public HowToDialog(Context context) {
        this.context = context;
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
        DialogHowToBinding binding = DialogHowToBinding.inflate(layoutInflater, container, false);
        binding.btnDismiss.setOnClickListener(v -> getDialog().dismiss());
        switch (alert){
            case ALERT_CARBOHYDRATE :
                binding.tvTitle.setText(getString(R.string.how_to_carbohydrate_title));
                binding.tvH1.setText(getString(R.string.how_to_carbohydrate_h1));
                binding.tvP1.setText(getString(R.string.how_to_carbohydrate_p1));
                binding.tvH2.setText(getString(R.string.how_to_carbohydrate_h2));
                binding.tvP2.setText(getString(R.string.how_to_carbohydrate_p2));
                binding.tvH3.setText(getString(R.string.how_to_carbohydrate_h3));
                binding.tvP3.setText(getString(R.string.how_to_carbohydrate_p3));
                break;
            case ALERT_SODIUM :
                binding.tvTitle.setText(getString(R.string.how_to_sodium_title));
                binding.tvH1.setText(getString(R.string.how_to_sodium_h1));
                binding.tvP1.setText(getString(R.string.how_to_sodium_p1));
                binding.tvH2.setText(getString(R.string.how_to_sodium_h2));
                binding.tvP2.setText(getString(R.string.how_to_sodium_p2));
                binding.tvH3.setVisibility(View.GONE);
                binding.tvP3.setVisibility(View.GONE);
                break;
            case ALERT_KCAL :
                binding.tvTitle.setText(getString(R.string.how_to_kcal_title));
                binding.tvH1.setText(getString(R.string.how_to_kcal_h1));
                binding.tvP1.setText(getString(R.string.how_to_kcal_p1));
                binding.tvH2.setText(getString(R.string.how_to_kcal_h2));
                binding.tvP2.setText(getString(R.string.how_to_kcal_p2));
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
