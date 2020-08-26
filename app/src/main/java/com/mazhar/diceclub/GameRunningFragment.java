package com.mazhar.diceclub;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameRunningFragment extends Fragment implements View.OnClickListener,IOnBackPressed {

    View view;
    ImageView runningGameGif,rollingDiceBefore;
    TextView rollButton;

    TextView textPlayerNameOne, playerNameOne, textResultOne, resultOne;
    TextView textPlayerNameTwo, playerNameTwo, textResultTwo, resultTwo;
    TextView textPlayerNameThree, playerNameThree, textResultThree, resultThree;
    TextView textPlayerNamerFour, playerNameFour, textResultFour, resultFour;
    TextView textPlayerNamerFive, playerNameFive, textResultFive, resultFive;
    TextView textPlayerNamerSix, playerNameSix, textResultSix, resultSix;

    LinearLayout llFirstUser, llSecondUser, llThirdUser, llFourthUser, llFifthUser, llSixthUser, llRollButton;

    Handler handler;
    RotateAnimation rotate;
    boolean rolling=false;
    Timer timer=new Timer();

    int[] winArray = new int[]{1,2,3,4,5,6};
    int[] lossArray = new int[]{3,4,5};
    int position = 1;
    int diceGeneratedResult = 0;

    List generatedResultList = new ArrayList();



    public GameRunningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_running, container, false);

        initializeViews();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                firstCick();

            }
        }, 5000);

        return view;

    }

    private void firstCick() {

        llFirstUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background_selected));
        textPlayerNameOne.setTextColor(Color.parseColor("#ffffff"));
        playerNameOne.setTextColor(Color.parseColor("#ffffff"));
        textResultOne.setTextColor(Color.parseColor("#ffffff"));
        resultOne.setTextColor(Color.parseColor("#ffffff"));
        resultOne.setText("Playing....");
        rollButton.performClick();

    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private void rollNextUser(int position) {

        if (position == 2)
        {
            llFirstUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background));
            textPlayerNameOne.setTextColor(Color.parseColor("#002147"));
            playerNameOne.setTextColor(Color.parseColor("#002147"));
            textResultOne.setTextColor(Color.parseColor("#002147"));
            resultOne.setTextColor(Color.parseColor("#002147"));
            resultOne.setText(diceGeneratedResult+"");

            llSecondUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background_selected));
            textPlayerNameTwo.setTextColor(Color.parseColor("#ffffff"));
            playerNameTwo.setTextColor(Color.parseColor("#ffffff"));
            textResultTwo.setTextColor(Color.parseColor("#ffffff"));
            resultTwo.setTextColor(Color.parseColor("#ffffff"));
            resultTwo.setText("Playing....");
        }
        else if (position == 3)
        {

            llSecondUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background));
            textPlayerNameTwo.setTextColor(Color.parseColor("#002147"));
            playerNameTwo.setTextColor(Color.parseColor("#002147"));
            textResultTwo.setTextColor(Color.parseColor("#002147"));
            resultTwo.setTextColor(Color.parseColor("#002147"));
            resultTwo.setText(diceGeneratedResult+"");

            llThirdUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background_selected));
            textPlayerNameThree.setTextColor(Color.parseColor("#ffffff"));
            playerNameThree.setTextColor(Color.parseColor("#ffffff"));
            textResultThree.setTextColor(Color.parseColor("#ffffff"));
            resultThree.setTextColor(Color.parseColor("#ffffff"));
            resultThree.setText("Playing....");
        }
        else if (position == 4)
        {
            llThirdUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background));
            textPlayerNameThree.setTextColor(Color.parseColor("#002147"));
            playerNameThree.setTextColor(Color.parseColor("#002147"));
            textResultThree.setTextColor(Color.parseColor("#002147"));
            resultThree.setTextColor(Color.parseColor("#002147"));
            resultThree.setText(diceGeneratedResult+"");

            llFourthUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background_selected));
            textPlayerNamerFour.setTextColor(Color.parseColor("#ffffff"));
            playerNameFour.setTextColor(Color.parseColor("#ffffff"));
            textResultFour.setTextColor(Color.parseColor("#ffffff"));
            resultFour.setTextColor(Color.parseColor("#ffffff"));
            resultFour.setText("Playing....");
        }
        else if (position == 5)
        {
            llFourthUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background));
            textPlayerNamerFour.setTextColor(Color.parseColor("#002147"));
            playerNameFour.setTextColor(Color.parseColor("#002147"));
            textResultFour.setTextColor(Color.parseColor("#002147"));
            resultFour.setTextColor(Color.parseColor("#002147"));
            resultFour.setText(diceGeneratedResult+"");

            llFifthUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background_selected));
            textPlayerNamerFive.setTextColor(Color.parseColor("#ffffff"));
            playerNameFive.setTextColor(Color.parseColor("#ffffff"));
            textResultFive.setTextColor(Color.parseColor("#ffffff"));
            resultFive.setTextColor(Color.parseColor("#ffffff"));
            resultFive.setText("Playing....");
        }

        else if (position == 6)
        {
            llFifthUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background));
            textPlayerNamerFive.setTextColor(Color.parseColor("#002147"));
            playerNameFive.setTextColor(Color.parseColor("#002147"));
            textResultFive.setTextColor(Color.parseColor("#002147"));
            resultFive.setTextColor(Color.parseColor("#002147"));
            resultFive.setText(diceGeneratedResult+"");

            llSixthUser.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.profile_background_selected));
            textPlayerNamerSix.setTextColor(Color.parseColor("#ffffff"));
            playerNameSix.setTextColor(Color.parseColor("#ffffff"));
            textResultSix.setTextColor(Color.parseColor("#ffffff"));
            resultSix.setTextColor(Color.parseColor("#ffffff"));
            resultSix.setText("Playing....");

            llRollButton.setVisibility(View.VISIBLE);
        }

    }

    void initializeViews() {
        runningGameGif = view.findViewById(R.id.runningGameGif);
        rollingDiceBefore = view.findViewById(R.id.rollingDiceBefore);

        textPlayerNameOne = view.findViewById(R.id.text_player_name_one);
        playerNameOne = view.findViewById(R.id.player_name_one);
        textResultOne = view.findViewById(R.id.text_result_one);
        resultOne = view.findViewById(R.id.result_one);

        textPlayerNameTwo = view.findViewById(R.id.text_player_name_two);
        playerNameTwo = view.findViewById(R.id.player_name_two);
        textResultTwo = view.findViewById(R.id.text_result_two);
        resultTwo = view.findViewById(R.id.result_two);

        textPlayerNameThree = view.findViewById(R.id.text_player_name_three);
        playerNameThree = view.findViewById(R.id.player_name_three);
        textResultThree = view.findViewById(R.id.text_result_three);
        resultThree = view.findViewById(R.id.result_three);

        textPlayerNamerFour = view.findViewById(R.id.text_player_name_four);
        playerNameFour = view.findViewById(R.id.player_name_four);
        textResultFour = view.findViewById(R.id.text_result_four);
        resultFour = view.findViewById(R.id.result_four);

        textPlayerNamerFive = view.findViewById(R.id.text_player_name_five);
        playerNameFive = view.findViewById(R.id.player_name_five);
        textResultFive = view.findViewById(R.id.text_result_five);
        resultFive = view.findViewById(R.id.result_five);

        textPlayerNamerSix = view.findViewById(R.id.text_player_name_six);
        playerNameSix = view.findViewById(R.id.player_name_six);
        textResultSix = view.findViewById(R.id.text_result_six);
        resultSix = view.findViewById(R.id.result_six);

        llFirstUser = view.findViewById(R.id.llFirstUser);
        llSecondUser = view.findViewById(R.id.llSecondUser);
        llThirdUser = view.findViewById(R.id.llThirdUser);
        llFourthUser = view.findViewById(R.id.llFourthUser);
        llFifthUser = view.findViewById(R.id.llFifthUser);
        llSixthUser = view.findViewById(R.id.llSixthUser);
        llRollButton = view.findViewById(R.id.llRollButton);


        rollButton = view.findViewById(R.id.rollButton);
        rollButton.setOnClickListener(this);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "JBold.ttf");
        textPlayerNameOne.setTypeface(tf);
        playerNameOne.setTypeface(tf);
        textResultOne.setTypeface(tf);
        resultOne.setTypeface(tf);

        rollButton.setTypeface(tf);

        textPlayerNameTwo.setTypeface(tf);
        playerNameTwo.setTypeface(tf);
        textResultTwo.setTypeface(tf);
        resultTwo.setTypeface(tf);

        textPlayerNameThree.setTypeface(tf);
        playerNameThree.setTypeface(tf);
        textResultThree.setTypeface(tf);
        resultThree.setTypeface(tf);

        textPlayerNamerFour.setTypeface(tf);
        playerNameFour.setTypeface(tf);
        textResultFour.setTypeface(tf);
        resultFour.setTypeface(tf);

        textPlayerNamerFive.setTypeface(tf);
        playerNameFive.setTypeface(tf);
        textResultFive.setTypeface(tf);
        resultFive.setTypeface(tf);

        textPlayerNamerSix.setTypeface(tf);
        playerNameSix.setTypeface(tf);
        textResultSix.setTypeface(tf);
        resultSix.setTypeface(tf);

        try
        {
            Glide.with(this)
                    .load(R.drawable.splash_gif)
                    .into(runningGameGif);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rollButton:
                if (position < 6)
                {
                    rollDice();
                }
                if (position==6)
                {
                    rollDice();
                }
                break;
        }
    }

    private void rollDice() {

        try
        {
            Glide.with(this)
                    .load(R.drawable.dice_game_running)
                    .into(rollingDiceBefore);

            if (position!=6)
            {
                if (position == 5)
                {
                    if (generatedResultList.contains("6"))
                        diceGeneratedResult = getRandom(winArray);
                    else
                        diceGeneratedResult = 6;
                }
                else
                {
                    diceGeneratedResult = getRandom(winArray);
                }

            }
            else
                diceGeneratedResult = getRandom(lossArray);

            generatedResultList.add(diceGeneratedResult);

        }
        catch (Exception e)
        {

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showResult();
            }
        }, 5000);

    }

    private void showResult() {

        if (position == 6)
        {
            resultSix.setText(diceGeneratedResult+"");
            rollButton.setVisibility(View.GONE);
            llRollButton.setVisibility(View.GONE);
        }

        switch (diceGeneratedResult)
        {
            case 1:
                rollingDiceBefore.setImageResource(R.drawable.one);
                break;
            case 2:
                rollingDiceBefore.setImageResource(R.drawable.two);
                break;
            case 3:
                rollingDiceBefore.setImageResource(R.drawable.three);
                break;
            case 4:
                rollingDiceBefore.setImageResource(R.drawable.four);
                break;
            case 5:
                rollingDiceBefore.setImageResource(R.drawable.five);
                break;
            case 6:
                rollingDiceBefore.setImageResource(R.drawable.six);
                break;
        }

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {

                rollNextUser(position);
                Glide.with(getContext())
                        .load(R.drawable.dice_game_before)
                        .into(rollingDiceBefore);
            }
        }, 2000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (position != 6)
                {
                    rollNextUser();
                }
            }
        }, 6000);
        position++;
    }

    private void rollNextUser() {
        rollButton.performClick();
    }

    public void onPause() {
        super.onPause();
    }
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public boolean onBackPressed() {
        Toast.makeText(getContext(), "back pressed", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showGameLossDialogue();
    }

    private void showGameLossDialogue() {

        new MaterialStyledDialog.Builder(getContext())
                .setTitle("No Sufficient Funds")
                .setDescription("You don't have sufficient Amount to Play this League. Please add money for play this league.")
                .setPositiveText("Add Money")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        PaymentFragment llf = new PaymentFragment();
                        ft.replace(R.id.frameLayout, llf);
                        ft.commit();
                    }
                })
                .setNegativeText("Ok")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();

    }

}