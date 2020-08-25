package com.shrikanthravi.customnavigationdrawer;

import android.content.Intent;
import android.os.Handler;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

public class SplashScreen extends BaseActivity {
    private LinearLayout llSplash;
    @Override
    public void initialize() {
        llSplash = (LinearLayout)inflater.inflate(R.layout.splash, null);//GODREJ
        llBody.addView(llSplash,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
