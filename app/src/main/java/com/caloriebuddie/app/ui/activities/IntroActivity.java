package com.caloriebuddie.app.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.caloriebuddie.app.R;
import com.caloriebuddie.app.ui.utils.UIHelper;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.Arrays;

/**
 * Created by pie on 23/01/2018.
 */

public class IntroActivity extends AppIntro {
    private final int SIGN_IN_REQUEST_CODE = 2018;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int whiteColor = android.graphics.Color.argb(255, 255, 255, 255);
        int greenColor = android.graphics.Color.argb(255, 0, 149, 103);

        this.addSlide(AppIntroFragment.newInstance("Stay Safe", "Roboto", "Calculate how much calories you gain from regular food labels.", "Roboto", R.drawable.ic_track_labels, whiteColor, greenColor, greenColor));
        this.addSlide(AppIntroFragment.newInstance("Track Your Fitness", "Roboto", "With regular fitness tracking, you can make informed lifestyle changes.", "Roboto", R.drawable.ic_track_fitness, whiteColor, greenColor, greenColor));
        this.addSlide(AppIntroFragment.newInstance("Meet Your Goals", "Roboto", "Know how to eat right to meet your fitness goals.", "Roboto", R.drawable.ic_track_goals, whiteColor, greenColor, greenColor));


        setBarColor(Color.parseColor("#009567"));
        setDoneText("Get Started");
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setSeparatorColor(Color.parseColor("#009567"));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        showLoginActivity();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        showLoginActivity();
    }

    private void showLoginActivity(){
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                        Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build())
                )
                .setTheme(R.style.AppTheme_Fullscreen)
                .setAllowNewEmailAccounts(true)
                .setLogo(R.drawable.splash_icon)
                .build();

        this.startActivityForResult(intent, SIGN_IN_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
//                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }else{
                if(response == null){
                    UIHelper.Dialogs.showMessageDialog(IntroActivity.this, "Login Error","An error occured during sign in.\n\nPlease try again later.");
                }

                if(response.getErrorCode() == ErrorCodes.NO_NETWORK){
                    UIHelper.Dialogs.showMessageDialog(IntroActivity.this, "Login Error","Your device seems to be offline.\n\nCheck network settings and try again.");
                }

                if(response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR){
                    UIHelper.Dialogs.showMessageDialog(IntroActivity.this, "Login Error","An error occured during sign in.\n\nPlease try again later.");
                }
            }
        }
    }
}
