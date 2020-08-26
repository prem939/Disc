package com.mazhar.diceclub;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static android.content.Context.MODE_PRIVATE;
import static com.mazhar.diceclub.LoginActivity.MyPREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    View view;
    ImageView diceGif;
    TextView startGame;
    SharedPreferences sharedpreferences;

    int leagueValue = 0;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        initializeViews();

        try
        {
            leagueValue = getArguments().getInt("leagueValue");
        }
        catch (Exception e)
        {

        }

        return view;

    }

    void initializeViews() {
        diceGif = view.findViewById(R.id.diceGif);
        startGame = view.findViewById(R.id.startGame);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "JBold.ttf");
        startGame.setTypeface(tf);

        startGame.setOnClickListener(this);

        try
        {
            Glide.with(this)
                    .load(R.drawable.game_dice_transparent)
                    .into(diceGif);

        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startGame:

                SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                int walletAmount = sh.getInt("Wallet", 0);
                walletAmount = walletAmount - leagueValue;

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("Wallet", walletAmount);
                editor.commit();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GameRunningFragment llf = new GameRunningFragment();
                ft.replace(R.id.frameLayout, llf);
                ft.commit();
                break;
        }
    }
}
