package com.caloriebuddie.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.caloriebuddie.app.R;

/**
 * Created by pie on 23/01/2018.
 */

public class SplashActivity extends BaseActivity {
    final long SPLASH_TIMEOUT = 3000;
    Thread splashTimerThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        startSplashTimer();
    }

    protected void startSplashTimer() {
        this.splashTimerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        splashTimerThread.sleep(SPLASH_TIMEOUT);
                    }
                } catch (InterruptedException e) {
                    showStartupActivity();
                } finally {
                    showStartupActivity();
                }
            }
        });

        this.splashTimerThread.start();
    }

    protected void showStartupActivity(){
        startActivity(new Intent(SplashActivity.this, IntroActivity.class));
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (splashTimerThread) {
                splashTimerThread.interrupt();
            }
        }
        return true;
    }
}
