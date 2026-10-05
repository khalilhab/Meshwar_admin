package com.meshwar.dashboard.ui.activities.terms.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.SUCCESS_ALERT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Term;
import com.meshwar.dashboard.databinding.ActivityTermsBinding;
import com.meshwar.dashboard.ui.activities.terms.presenter.TermsPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;


public class TermsActivity extends BaseActivity implements TermsView {

    private ActivityTermsBinding binding;

    private TermsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new TermsPresenter(this, this);

        initView();
        initData();
        initListener();
    }

    @Override
    public void initView() {

        binding.edDescription.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public void initData() {
        presenter.getTerm();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());
        binding.btnSaveEdits.setOnClickListener(v -> {
            presenter.editTerms(binding.edDescription.getText().toString());
        });
        binding.edDescription.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (binding.edDescription.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void InitTerm(Term term) {
        binding.edDescription.setText(term.getDescription());
    }

    @Override
    public void termsEdited() {
        showMessage(getString(R.string.updated_successfully),SUCCESS_ALERT);
        finish();
    }
}