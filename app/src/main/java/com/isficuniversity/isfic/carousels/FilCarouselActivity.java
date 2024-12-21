package com.isficuniversity.isfic.carousels;

import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.adaptaters.ImageAdapter;

import java.util.Arrays;
import java.util.List;

public class FilCarouselActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ProgressBar[] progressBars = new ProgressBar[8]; // Correctly initialize the array
    private Handler handler = new Handler();
    private Runnable autoScrollRunnable;
    private int currentPage = 0;
    private List<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fil_carousel);

        // Configure Shared Element Transitions
        getWindow().setSharedElementEnterTransition(
                TransitionInflater.from(this).inflateTransition(android.R.transition.move));
        getWindow().setSharedElementReturnTransition(
                TransitionInflater.from(this).inflateTransition(android.R.transition.move));

        viewPager = findViewById(R.id.viewPager);
        images = Arrays.asList(
                R.drawable.reseautele, R.drawable.douane,
                R.drawable.compta, R.drawable.maketng, R.drawable.marketdig,
                R.drawable.gea, R.drawable.gesti, R.drawable.cominter, R.drawable.comjour
        );

        // Setup ImageAdapter for ViewPager
        ImageAdapter adapter = new ImageAdapter(this, images);
        viewPager.setAdapter(adapter);

        // Initialize ProgressBars
        progressBars[0] = findViewById(R.id.progressBar1);
        progressBars[1] = findViewById(R.id.progressBar2);
        progressBars[2] = findViewById(R.id.progressBar3);
        progressBars[3] = findViewById(R.id.progressBar4);
        progressBars[4] = findViewById(R.id.progressBar5);
        progressBars[5] = findViewById(R.id.progressBar6);
        progressBars[6] = findViewById(R.id.progressBar7);
        progressBars[7] = findViewById(R.id.progressBar8);

        // Ensure all ProgressBars are properly initialized
        for (int i = 0; i < progressBars.length; i++) {
            if (progressBars[i] == null) {
                throw new IllegalStateException("ProgressBar with ID R.id.progressBar" + (i + 1) + " is missing in the XML file.");
            }
        }

        resetProgressBars();

        // Auto-scroll logic
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                currentPage = (currentPage + 1) % images.size();
                updateProgressBar();
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, 8000);
            }
        };
        handler.postDelayed(autoScrollRunnable, 8000);

        // Manual click to navigate
        findViewById(R.id.carouselLeft).setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                updateProgressBar();
                viewPager.setCurrentItem(currentPage, true);
            }
        });

        findViewById(R.id.carouselRight).setOnClickListener(v -> {
            if (currentPage < images.size() - 1) {
                currentPage++;
                updateProgressBar();
                viewPager.setCurrentItem(currentPage, true);
            }
        });
    }

    private void resetProgressBars() {
        for (ProgressBar progressBar : progressBars) {
            if (progressBar != null) {
                progressBar.setProgress(0);
            }
        }
    }

    private void updateProgressBar() {
        resetProgressBars();
        if (currentPage < progressBars.length) {
            progressBars[currentPage].setProgress(100);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoScrollRunnable);
    }
}