package com.meshwar.dashboard.ui.activities.intro.view;

import static com.meshwar.dashboard.utils.AppSharedConstants.INFO_ALERT;
import static com.meshwar.dashboard.utils.AppSharedConstants.INTRODUCTION_TYPE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.meshwar.dashboard.R;
import com.meshwar.dashboard.data.network.model.Intro;
import com.meshwar.dashboard.databinding.ActivityIntroBinding;
import com.meshwar.dashboard.ui.adapter.AdapterIntro;
import com.meshwar.dashboard.ui.activities.intro.presenter.IntroPresenter;
import com.meshwar.dashboard.ui.base.BaseActivity;
import com.meshwar.dashboard.utils.AppSharedConstants;
import com.meshwar.dashboard.utils.AppSharedMethod;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends BaseActivity implements IntroView, AdapterIntro.onItemClicked {

    IntroPresenter presenter;
    ActivityIntroBinding binding;


    LinearLayoutManager layoutManager;
    AdapterIntro adapterIntro;
    List<Intro> intros = new ArrayList<>();

    String introductionType = "";

    ActivityResultLauncher<Intent> launcherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new IntroPresenter(context, this);

        initView();
        initData();
        initListener();
    }


    @Override
    public void initView() {
        if (getIntent() != null)
            if (getIntent().hasExtra(INTRODUCTION_TYPE))
                introductionType = getIntent().getStringExtra(INTRODUCTION_TYPE);


        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.rcIntro.setLayoutManager(layoutManager);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.rcIntro);

        binding.rcIntro.setAdapter(adapterIntro = new AdapterIntro(this, intros, this));


    }

    @Override
    public void initData() {
        presenter.getIntroduction(introductionType);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void initListener() {
        binding.imvBack.setOnClickListener(v -> onBackPressed());

        launcherImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Uri uri = AppSharedMethod.getSelectedImageUri(context, result.getResultCode(), result.getData());
            if (uri != null) {
                intros.get(adapterIntro.getSelectedPosition()).setNewImageUri(uri);
                adapterIntro.notifyDataSetChanged();
            }
        });

        binding.imvNext.setOnClickListener(v -> {
            binding.rcIntro.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1);
        });
        binding.imvPrevious.setOnClickListener(v -> {
            binding.rcIntro.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() - 1);
        });

        binding.linearAddNewPage.setOnClickListener(v -> binding.rcIntro.smoothScrollToPosition(0));

        binding.rcIntro.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    binding.linearAddNewPage.setVisibility(layoutManager.findFirstVisibleItemPosition() == 0 ? View.INVISIBLE : View.VISIBLE);

                    if (layoutManager.findFirstVisibleItemPosition() == 0 && intros.size() == 1) {
                        binding.imvPrevious.setVisibility(View.INVISIBLE);
                        binding.imvNext.setVisibility(View.INVISIBLE);
                    }
                    if (layoutManager.findFirstVisibleItemPosition() == 0 && intros.size() > 1) {
                        binding.imvPrevious.setVisibility(View.INVISIBLE);
                        binding.imvNext.setVisibility(View.VISIBLE);
                    }
                    if (layoutManager.findFirstVisibleItemPosition() == intros.size() - 1) {
                        binding.imvPrevious.setVisibility(View.VISIBLE);
                        binding.imvNext.setVisibility(View.INVISIBLE);
                    }
                    if (layoutManager.findFirstVisibleItemPosition() > 0 && layoutManager.findFirstVisibleItemPosition() < intros.size() - 1) {
                        binding.imvPrevious.setVisibility(View.VISIBLE);
                        binding.imvNext.setVisibility(View.VISIBLE);

                    }

                }
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void InitIntroductions(List<Intro> intros) {
        this.intros.clear();
        this.intros.addAll(intros);
        this.intros.add(0, new Intro());
//        adapterIntro.setSelectedPosition();
//        binding.rcIntro.scrollToPosition(0);
        adapterIntro.notifyDataSetChanged();

        if (intros.size() > 0 && adapterIntro.getSelectedPosition()==0)
            binding.imvNext.setVisibility(View.VISIBLE);


//        if (intros.size()==1){
//            binding.imvPrevious.setVisibility(View.INVISIBLE);
//            binding.imvNext.setVisibility(View.INVISIBLE);
//        } else {
//            binding.imvNext.setVisibility(View.VISIBLE);
//        }


    }


    @Override
    public void setActionType(String actionType) {
        if (actionType.equals("add_image")) {
            Intent intent = AppSharedMethod.checkPermissionStorage(context, AppSharedConstants.REQUEST_PERMISSIONS_CODE_IMAGE_IMAGE, AppSharedConstants.TYPE_IMAGE, AppSharedConstants.TYPE_ALL);
            if (intent != null) {
                launcherImage.launch(intent);
            }
        } else if (actionType.equals("add")) {
            if (adapterIntro.getSelectedPosition() == 0 && intros.get(adapterIntro.getSelectedPosition()).getNewImageUri() == null) {
                showMessage(getString(R.string.you_must_add_an_image_to_the_introductions_page), INFO_ALERT);
            } else if (TextUtils.isEmpty(intros.get(adapterIntro.getSelectedPosition()).getDescription())) {
                showMessage(getString(R.string.enter_the_description), INFO_ALERT);
            } else {
                presenter.addIntroduction(introductionType, intros.get(adapterIntro.getSelectedPosition()));
            }
        } else if (actionType.equals("delete")) {
            presenter.deleteIntroduction(introductionType, intros.get(adapterIntro.getSelectedPosition()).getUuid());
        } else if (actionType.equals("edit")) {
            if (adapterIntro.getSelectedPosition() == 0 && intros.get(adapterIntro.getSelectedPosition()).getNewImageUri() == null) {
                showMessage(getString(R.string.you_must_add_an_image_to_the_introductions_page), INFO_ALERT);
            } else if (TextUtils.isEmpty(intros.get(adapterIntro.getSelectedPosition()).getDescription())) {
                showMessage(getString(R.string.enter_the_description), INFO_ALERT);
            } else {
                presenter.editIntroduction(introductionType, intros.get(adapterIntro.getSelectedPosition()));
            }
        }
    }
}
