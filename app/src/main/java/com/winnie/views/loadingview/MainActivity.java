package com.winnie.views.loadingview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * @author winnie
 */
public class MainActivity extends AppCompatActivity {

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingView = new LoadingView(this);
        mLoadingView.show();
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mLoadingView.success();
            }
        };
        timer.start();
    }
}
