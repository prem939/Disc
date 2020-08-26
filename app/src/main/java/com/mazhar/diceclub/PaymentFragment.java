package com.mazhar.diceclub;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.mazhar.diceclub.LoginActivity.MyPREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements View.OnClickListener {

    View view;
    ImageView gPay, phonePe, payTm;
    TextView textPaymentMethod;
    final int UPI_PAYMENT = 0;
    private String str_topay = "0", str_paymentOption = "", str_packageName = "";

    TextView textGooglePe, textPhonePe, textPayTm, textWalletAmount, walletAmount, textRS;

    LoginActivity llGooglePe, llPhonePe, llPayTm;

    SharedPreferences sharedpreferences;

    int leagueValue = 0;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment, container, false);

        initializeViews();

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        try {
            leagueValue = getArguments().getInt("leagueValue");
        }
        catch (Exception e)
        {

        }

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "JBold.ttf");
        textPaymentMethod.setTypeface(tf);
        textGooglePe.setTypeface(tf);
        textPhonePe.setTypeface(tf);
        textPayTm.setTypeface(tf);
        textWalletAmount.setTypeface(tf);
        walletAmount.setTypeface(tf);

        textRS.setTypeface(tf);

        return view;

    }

    void initializeViews() {
        view.findViewById(R.id.gPay).setOnClickListener(this);
        view.findViewById(R.id.phonePe).setOnClickListener(this);
        view.findViewById(R.id.payTm).setOnClickListener(this);
        textRS = view.findViewById(R.id.text_RS);

        view.findViewById(R.id.llGooglePay).setOnClickListener(this);
        view.findViewById(R.id.llPhonePe).setOnClickListener(this);
        view.findViewById(R.id.llPayTm).setOnClickListener(this);

        textGooglePe = view.findViewById(R.id.text_googlepe);
        textPhonePe = view.findViewById(R.id.text_phonepe);
        textPayTm = view.findViewById(R.id.text_paytm);
        textWalletAmount = view.findViewById(R.id.text_wallet_amount);
        walletAmount = view.findViewById(R.id.wallet_amount);

        textPaymentMethod = view.findViewById(R.id.text_payment_method);

        SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int walletAmountt = sh.getInt("Wallet", 0);

        walletAmount.setText(walletAmountt+"");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payTm:
                str_paymentOption = "phonepe";
                payUsingUpi("25", "9052332194@ybl", "Mazhar", "You need to pay");
                break;
            case R.id.phonePe:
                str_paymentOption = "phonepe";
                payUsingUpi("25", "9052332194@ybl", "Mazhar", "You need to pay");
                break;
            case R.id.gPay:
                str_paymentOption = "googlePay";
                payUsingUpi("1", "6300672612@ybl", "Mazhar", "You need to pay");
                break;
            case R.id.llGooglePay:
                str_paymentOption = "googlePay";
                payUsingUpi("1", "6300672612@ybl", "Mazhar", "You need to pay");
                break;

            case R.id.llPhonePe:
                str_paymentOption = "phonepe";
                payUsingUpi("25", "9052332194@ybl", "Mazhar", "You need to pay");
                break;

            case R.id.llPayTm:
                str_paymentOption = "paytm";
                payUsingUpi("25", "6300672612@ybl", "Mazhar", "You need to pay");
                break;
        }
    }

    void payUsingUpi(String userAmount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", userAmount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        if (str_paymentOption == "googlePay") {
            str_packageName = "com.google.android.apps.nbu.paisa.user";
        } else if (str_paymentOption == "phonepe") {
            str_packageName = "com.phonepe.app";
        } else if (str_paymentOption == "paytm") {
            str_packageName = "net.one97.paytm";
        }

        if (IsAppInstalled(str_packageName)) {
            upiPayIntent.setPackage(str_packageName);
            startActivityForResult(upiPayIntent, UPI_PAYMENT);
        }else {
            Toast.makeText(getContext(), "App is not Present", Toast.LENGTH_SHORT).show();
        }

        // will always show a dialog to user to choose an app
//        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//
//        // check if intent resolves
//        if (null != chooser.resolveActivity(getPackageManager())) {
//            startActivityForResult(chooser, UPI_PAYMENT);
//        } else {
//            Toast.makeText(PaymentActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
//        }

    }

    private boolean IsAppInstalled(String packageName) {
        PackageManager pm = getActivity().getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(getContext())) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String[] equalStr = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.


                Toast.makeText(getContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GameFragment llf = new GameFragment();
                ft.replace(R.id.frameLayout, llf);
                ft.commit();

                SharedPreferences sh = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                int walletAmount = sh.getInt("Wallet", 0);

                walletAmount = walletAmount+leagueValue;

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("Wallet", walletAmount);
                editor.commit();



                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(getContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }
}
