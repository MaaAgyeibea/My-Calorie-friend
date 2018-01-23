package com.caloriebuddie.app.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.caloriebuddie.app.core.App;

/**
 * Created by pie on 23/01/2018.
 */

public class BaseActivity extends AppCompatActivity {
    App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
    }

    public App getApp() {
        return app;
    }
}
