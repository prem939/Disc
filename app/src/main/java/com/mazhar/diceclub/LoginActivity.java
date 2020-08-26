package com.mazhar.diceclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity {
    private LinearLayout llLogin;
    private EditText etUserName, etPhoneNo, etOtp;
    private Button btnGenerateOtp;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private static String code = "", phoneNumber = "", otp = "", verificationCode="";
    FirebaseAuth auth;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String FIRST_TIME_LOGIN = "firstTimeLogin";


    @Override
    public void initialize() {
        llLogin = (LinearLayout) inflater.inflate(R.layout.login, null);
        llBody.addView(llLogin, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        etUserName = llLogin.findViewById(R.id.etUserName);
        etPhoneNo = llLogin.findViewById(R.id.etPhoneNo);
        etOtp = llLogin.findViewById(R.id.etOtp);
        btnGenerateOtp = llLogin.findViewById(R.id.btnotp);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "JBold.ttf");
        etUserName.setTypeface(tf);
        etPhoneNo.setTypeface(tf);
        etOtp.setTypeface(tf);
        btnGenerateOtp.setTypeface(tf);

        btnGenerateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = etPhoneNo.getText().toString();
                if (phoneNumber.length() >= 10) {
                    auth = FirebaseAuth.getInstance();
                    etOtp.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phoneNumber, 60, TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                            Toast.makeText(LoginActivity.this, "verification completed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            super.onCodeSent(s, forceResendingToken);
                            code = s;
                            verificationCode = s;

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(FIRST_TIME_LOGIN, false);
                            editor.commit();

                            startActivity(new Intent(LoginActivity.this, NavigationBarActivity.class));
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please Enter a valid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
