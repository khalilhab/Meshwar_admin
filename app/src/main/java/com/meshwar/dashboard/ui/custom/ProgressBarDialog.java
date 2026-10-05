package com.meshwar.dashboard.ui.custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Objects;

public class ProgressBarDialog extends AlertDialog {

    private final Context context;
    private ProgressBar progressBar;

    public ProgressBarDialog(Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mView = new LinearLayout(context);
        mView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mView.setGravity(Gravity.CENTER);
        progressBar = new ProgressBar(context);
        mView.addView(progressBar);
        setContentView(mView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setDimAmount(0.5f);
    }

    public void setDimAmount(float amount) {
        progressBar.setVisibility(amount > 0 ? View.VISIBLE : View.GONE);
        getWindow().setDimAmount(amount);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
