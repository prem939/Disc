package com.mazhar.diceclub;


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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFragment extends Fragment implements View.OnClickListener{

    View view;
    TextView textWalletAmount, walletAmount, ruleOne, ruleTwo, textPaymentMethodWithdraw, withdrawButton, textRS;
    EditText upi, amount;

    public WithdrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_withdraw, container, false);

        initializeViews();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return view;

    }

    void initializeViews() {

        textWalletAmount = view.findViewById(R.id.text_wallet_amount);
        walletAmount = view.findViewById(R.id.wallet_amount);
        ruleOne = view.findViewById(R.id.rule_one);
        ruleTwo = view.findViewById(R.id.rule_two);
        textPaymentMethodWithdraw = view.findViewById(R.id.text_payment_method_withdraw);
        upi = view.findViewById(R.id.upi);
        amount = view.findViewById(R.id.amount);
        withdrawButton = view.findViewById(R.id.withdrawButton);
        withdrawButton.setOnClickListener(this);
        textRS = view.findViewById(R.id.text_RS);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "JBold.ttf");
        textWalletAmount.setTypeface(tf);
        walletAmount.setTypeface(tf);
        ruleOne.setTypeface(tf);
        ruleTwo.setTypeface(tf);
        textPaymentMethodWithdraw.setTypeface(tf);
        upi.setTypeface(tf);
        amount.setTypeface(tf);
        withdrawButton.setTypeface(tf);
        textRS.setTypeface(tf);

        SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int walletAmountt = sh.getInt("Wallet", 0);

        walletAmount.setText(walletAmountt+"");

//        TastyToast.makeText(getContext(), "You don't have sufficient Amount", TastyToast.LENGTH_LONG, TastyToast.WARNING);

    }

    public void setText(String amount){

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.withdrawButton:
                SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                int walletAmount = sh.getInt("Wallet", 0);
                if (amount.getText().toString().isEmpty() && upi.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Please fill fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (walletAmount<100)
                    {
                        showinsufficientMoneyDialogue();
                    }
                    else
                    {
                        showWithdrawMoneyDialogue();
                    }
                }
                break;
        }

    }

    private void showWithdrawMoneyDialogue() {

        new MaterialStyledDialog.Builder(getContext())
                .setTitle("Withdraw Request")
                .setDescription("Rs.5000 Withdraw Request Placed Succesfully. Within 15 Minuted You will be recieve money in your bank.")
                .setNegativeText("Ok")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();

    }

    private void showinsufficientMoneyDialogue() {

        new MaterialStyledDialog.Builder(getContext())
                .setTitle("Withdraw Request")
                .setDescription("You don't have sufficient amount in wallet to withdraw.")
                .setNegativeText("Ok")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();

    }

}
