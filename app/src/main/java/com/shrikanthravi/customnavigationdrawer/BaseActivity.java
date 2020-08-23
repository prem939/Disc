package com.shrikanthravi.customnavigationdrawer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends FragmentActivity {
    public LayoutInflater inflater;
    public LinearLayout llBody;
    public Preference preference;
    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        preference = new Preference(getApplicationContext());
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
