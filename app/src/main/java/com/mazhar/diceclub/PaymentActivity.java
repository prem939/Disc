package com.mazhar.diceclub;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PaymentActivity extends BaseActivity {
    LinearLayout llpayment;
    private TextView txt_50;
    private Button btn_pay;
    private String str_topay = "0", str_paymentOption = "", str_packageName = "";
    final int UPI_PAYMENT = 0;
    private RadioGroup radioGroup;
    private RadioButton rb_googlepay, rb_phonepe, rb_paytm;

    @Override
    public void initialize() {
        llpayment = (LinearLayout) inflater.inflate(R.layout.payment, null);
        llBody.addView(llpayment, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        txt_50 = llpayment.findViewById(R.id.txt_50);
        btn_pay = llpayment.findViewById(R.id.btn_pay);
        radioGroup = (RadioGroup) llpayment.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        rb_googlepay = llpayment.findViewById(R.id.rb_googlepay);
        rb_phonepe = llpayment.findViewById(R.id.rb_phonepe);
        rb_paytm = llpayment.findViewById(R.id.rb_paytm);

        if (getIntent().getStringExtra("amount") != null) {
            str_topay = getIntent().getStringExtra("amount");
            txt_50.setText(getIntent().getStringExtra("amount") + " ₹");
        }

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (str_paymentOption != null)
                    payUsingUpi(str_topay, "6300672612@ybl", "Mazhar", "You need to pay");
                else
                    Toast.makeText(PaymentActivity.this, "Click any Payment option", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void OnRadioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        str_paymentOption = "";
        switch (view.getId()) {
            case R.id.rb_googlepay:
//                if (checked)
//                    str_paymentOption = "googlePay";
//                rb_paytm.setChecked(false);
//                rb_phonepe.setChecked(false);
                break;
            case R.id.rb_paytm:
                if (checked)
                    str_paymentOption = "paytm";
                rb_googlepay.setChecked(false);
                rb_phonepe.setChecked(false);
                break;
            case R.id.rb_phonepe:
                if (checked)
                    str_paymentOption = "phonepe";
                rb_paytm.setChecked(false);
                rb_googlepay.setChecked(false);
                break;
        }
//        Toast.makeText(PaymentActivity.this, str_paymentOption, Toast.LENGTH_SHORT).show();
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
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
            Toast.makeText(PaymentActivity.this, "App is not Present", Toast.LENGTH_SHORT).show();
            finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        if (isConnectionAvailable(PaymentActivity.this)) {
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
                Toast.makeText(PaymentActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaymentActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
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

    private boolean IsAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }
}
