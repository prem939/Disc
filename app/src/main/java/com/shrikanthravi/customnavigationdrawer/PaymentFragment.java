package com.shrikanthravi.customnavigationdrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements View.OnClickListener {

    EditText edt_custompay;
    TextView txt_50, txt_100, txt_25, txt_topay;
    Button btn_pay;
    View view;
    String str_amount = "" + 0;
    final int UPI_PAYMENT = 0;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment, container, false);

        initializeViews();

        txt_50.setOnClickListener(this);
        txt_25.setOnClickListener(this);
        txt_100.setOnClickListener(this);
        edt_custompay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                txt_topay.setBackgroundColor(getResources().getColor(R.color.pure_black));
                txt_topay.setText(edt_custompay.getText().toString() + " ₹");
                str_amount = edt_custompay.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_pay.setOnClickListener(this);

        return view;

    }

    void initializeViews() {
        edt_custompay = view.findViewById(R.id.edt_custom);
        txt_50 = view.findViewById(R.id.txt_50);
        txt_100 = view.findViewById(R.id.txt_100);
        txt_topay = view.findViewById(R.id.txt_topay);
        txt_25 = view.findViewById(R.id.txt_25);
        btn_pay = view.findViewById(R.id.btn_pay);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_50:
                setText("50");
                break;
            case R.id.txt_25:
                setText("25");
                break;
            case R.id.txt_100:
                setText("100");
                break;
            case R.id.btn_pay:
                Intent intent = new Intent(getActivity().getApplication(), PaymentActivity.class);
                intent.putExtra("amount", str_amount);
                getActivity().startActivity(intent);
                break;
        }
    }
    public void setText(String amount){
        txt_topay.setText(amount + " ₹");
        str_amount = amount;
    }
}
