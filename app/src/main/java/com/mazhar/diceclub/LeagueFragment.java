package com.mazhar.diceclub;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import static android.content.Context.MODE_PRIVATE;
import static com.mazhar.diceclub.LoginActivity.MyPREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueFragment extends Fragment implements View.OnClickListener{

    View view;
    TextView firstLeague,secondLeague,thirdLeague;
    ImageView share;
    TextView textWhatsappShare;

    TextView textWalletAmount, walletAmount, depositButton, textCurrentlyRunningLeagues, textRS;

    SharedPreferences sharedpreferences;

    public LeagueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_league, container, false);

        initializeViews();

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        return view;

    }

    void initializeViews() {

        firstLeague = view.findViewById(R.id.firstLeague);
        secondLeague = view.findViewById(R.id.secondLeague);
        thirdLeague = view.findViewById(R.id.thirdLeague);
        view.findViewById(R.id.share).setOnClickListener(this);
        textRS = view.findViewById(R.id.text_RS);

        firstLeague.setOnClickListener(this);
        secondLeague.setOnClickListener(this);
        thirdLeague.setOnClickListener(this);

        textWhatsappShare = view.findViewById(R.id.text_whatsapp_share);

        textWalletAmount = view.findViewById(R.id.text_wallet_amount);
        walletAmount = view.findViewById(R.id.wallet_amount);
        depositButton = view.findViewById(R.id.depositButton);
        depositButton.setOnClickListener(this);

        textCurrentlyRunningLeagues = view.findViewById(R.id.text_currently_running_leagues);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "JBold.ttf");
        textWhatsappShare.setTypeface(tf);
        firstLeague.setTypeface(tf);
        secondLeague.setTypeface(tf);
        thirdLeague.setTypeface(tf);

        textRS.setTypeface(tf);

        textWalletAmount.setTypeface(tf);
        walletAmount.setTypeface(tf);
        depositButton.setTypeface(tf);
        textCurrentlyRunningLeagues.setTypeface(tf);

        SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int walletAmountt = sh.getInt("Wallet", 0);

        walletAmount.setText(walletAmountt+"");


    }

    public void setText(String amount){

    }

    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.depositButton:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                PaymentFragment llf = new PaymentFragment();
                ft.replace(R.id.frameLayout, llf);
                ft.commit();
                break;
            case R.id.share:

                SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                int walletAmount = sh.getInt("Wallet", 0);

                sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean WhatsappRewardApplied = sh.getBoolean("WhatsappRewardApplied", false);

                if (!WhatsappRewardApplied)
                {
                    walletAmount = walletAmount+5;

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("Wallet", walletAmount);
                    editor.commit();
                }


                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("WhatsappRewardApplied", true);
                editor.commit();

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.thirdLeague:
                sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                walletAmount = sh.getInt("Wallet", 0);
                if (walletAmount<100)
                    showAddMoneyDialogue(100);
                else
                    navigateToGameScreen(100);
                break;
            case R.id.secondLeague:
                sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                walletAmount = sh.getInt("Wallet", 0);
                if (walletAmount<50)
                    showAddMoneyDialogue(50);
                else
                    navigateToGameScreen(50);
                break;
            case R.id.firstLeague :
                sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                walletAmount = sh.getInt("Wallet", 0);
                if (walletAmount<25)
                    showAddMoneyDialogue(25);
                else
                    navigateToGameScreen(25);
                break;


        }

    }

    private void navigateToGameScreen(int leagueValue) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        GameFragment llf = new GameFragment();

        Bundle args = new Bundle();
        args.putInt("leagueValue", leagueValue);
        llf.setArguments(args);

        ft.replace(R.id.frameLayout, llf);
        ft.commit();

    }

    private void showAddMoneyDialogue(final int leagueValue) {

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

                        Bundle args = new Bundle();
                        args.putInt("leagueValue", leagueValue);
                        llf.setArguments(args);

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
