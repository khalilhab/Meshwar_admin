package com.meshwar.dashboard.utils.helper;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import com.meshwar.dashboard.R;

import java.util.ArrayList;
import java.util.List;

public class SliderDots {

    private Context context;
    private List<ImageView> dots;
    private int count;
    private int colorDot;
    private int selected = -1;
    private Handler handler;
    private Runnable runnable;

    public SliderDots(Context context, final RecyclerView recyclerView, final LinearLayoutManager linearLayoutManager, LinearLayout dotsLayout, int colorDot) {
        this.context = context;
        this.count = linearLayoutManager.getItemCount();
        this.dots = new ArrayList<>();
        this.colorDot = colorDot;

        SnapHelper snapHelper = new PagerSnapHelper();
        try {
            recyclerView.setOnFlingListener(null);
            snapHelper.attachToRecyclerView(recyclerView);
            dotsLayout.removeAllViews();

            handler = new Handler();
            runnable = () -> {
                recyclerView.smoothScrollToPosition(selected);
                handler.postDelayed(runnable, 3000);
            };

            for (int i = 0; i < count; i++) {
                ImageView dot = new ImageView(context);
                dot.setImageResource(R.drawable.bg_slider);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._20sdp),
                        (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._3sdp)
                );
                params.setMargins(2, 0, 2, 0);
                dotsLayout.addView(dot, params);

                dots.add(dot);
            }
            selectDot(0);
            handler.postDelayed(runnable, 3000);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (newState == SCROLL_STATE_IDLE) {
                        if (handler != null && runnable != null)
                            handler.removeCallbacks(runnable);
                        selected = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                        selectDot(selected);
                        handler.postDelayed(runnable, 3000);
                    }
                }
            });
        }catch (IllegalStateException e){
            Log.e("ttt", "SliderDots " + "An instance of OnFlingListener already set.");
        }


    }

    private void selectDot(int idx) {
        for (int i = 0; i < count; i++) {
            dots.get(i).setImageResource(R.drawable.bg_slider);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._20sdp),
                    (int) context.getResources().getDimension(com.intuit.sdp.R.dimen._3sdp)
            );
            params.setMargins(2, 0, 2, 0);
            dots.get(i).setLayoutParams(params);
            dots.get(i).setColorFilter(ContextCompat.getColor(context, (i == idx) ? colorDot : R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);

        }

        selected = idx + 1 < count ? ++idx : 0;
    }

}
