package com.isficuniversity.isfic.carousels;

import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionInflater;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.isficuniversity.isfic.R;
import com.isficuniversity.isfic.adaptaters.ImageAdapter;

import java.util.Arrays;
import java.util.List;

public class DepCarouselActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ProgressBar[] progressBars = new ProgressBar[2]; // Correctly initialize the array
    private Handler handler = new Handler();
    private Runnable autoScrollRunnable;
    private int currentPage = 0;
    private List<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dep_carousel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configure Shared Element Transitions
        getWindow().setSharedElementEnterTransition(
                TransitionInflater.from(this).inflateTransition(android.R.transition.move));
        getWindow().setSharedElementReturnTransition(
                TransitionInflater.from(this).inflateTransition(android.R.transition.move));

        viewPager = findViewById(R.id.viewPager);
        images = Arrays.asList(
                R.drawable.refactor1, R.drawable.refactor
        );

        ImageAdapter adapter = new ImageAdapter(this, images);
        viewPager.setAdapter(adapter);

        progressBars[0] = findViewById(R.id.progressBar1);
        progressBars[1] = findViewById(R.id.progressBar2);

       for (int i = 0; i < progressBars.length; i++) {
            if (progressBars[i] == null) {
                throw new IllegalStateException("ProgressBar with ID R.id.progressBar" + (i + 1) + " is missing in the XML file.");
            }
        }

        resetProgressBars();

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