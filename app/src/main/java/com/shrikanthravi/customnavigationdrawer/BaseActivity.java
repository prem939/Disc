package com.shrikanthravi.customnavigationdrawer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.graphics.drawable.BitmapDrawable;

import com.google.firebase.FirebaseApp;

public abstract class BaseActivity extends FragmentActivity {
    public LayoutInflater inflater;
    public LinearLayout llBody;
    public Preference preference;
    protected Dialog dialog;
    private Animation rotateXaxis;
    private ImageView ivOutsideImage, ImageView01;
    public boolean isCancelableLoader;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        preference = new Preference(getApplicationContext());

        FirebaseApp.initializeApp(this);
        inflater = this.getLayoutInflater();
        initialization();
        initialize();
    }
    public abstract void initialize();

    private void initialization() {
        llBody = findViewById(R.id.llBody);
    }

    public void hideKeyBoard(View v) {
        if (v != null && getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
