package com.winnie.views.loadingview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

/**
 * @author winnie
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadingSuccess(View view) {
        startActivity(new Intent(this, LoadingSuccessActivity.class));
    }

    public void loadingError(View view) {
        startActivity(new Intent(this, LoadingErrorActivity.class));
    }
}
