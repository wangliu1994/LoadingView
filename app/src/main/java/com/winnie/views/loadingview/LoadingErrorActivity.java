package com.winnie.views.loadingview;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author winnie
 */
public class LoadingErrorActivity extends AppCompatActivity {

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mLoadingView = new LoadingView(this);
        mLoadingView.show();
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mLoadingView.error();
            }
        };
        timer.start();
    }
}
