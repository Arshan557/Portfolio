package com.portfolio.arshan.portfolio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

public class Education extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CircularProgressBar c1 = (CircularProgressBar) findViewById(R.id.btechProgressBar);
        final CircularProgressBar c2 = (CircularProgressBar) findViewById(R.id.diplomaProgressBar);
        final CircularProgressBar c3 = (CircularProgressBar) findViewById(R.id.sscProgressBar);

        c1.animateProgressTo(0, 71, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                c1.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                c1.setSubTitle("Distinction");
            }
        });

        c2.animateProgressTo(0, 79, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                c2.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                c2.setSubTitle("Distinction");
            }
        });

        c3.animateProgressTo(0, 84, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                c3.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                c3.setSubTitle("Distinction");
            }
        });

    }

}
