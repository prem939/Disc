package com.mazhar.diceclub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ActivitySplashScreen extends Activity {

    ImageView splashIcon;
//    TextView splashName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashIcon = findViewById(R.id.splashIcon);

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "JBold.ttf");

        try
        {
            Glide.with(this)
                    .load(R.drawable.splash_gif)
                    .into(splashIcon);

        }
        catch (Exception e)
        {

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sh = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean checkFirstTime = sh.getBoolean("firstTimeLogin", true);
                if (checkFirstTime)
                {
                    Intent intent = new Intent(ActivitySplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(ActivitySplashScreen.this,NavigationBarActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}