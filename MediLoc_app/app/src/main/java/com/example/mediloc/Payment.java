package com.example.mediloc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Calendar;

public class Payment extends AppCompatActivity implements View.OnClickListener,JsonResponse {
    String amt,card,cvv,dt;
    Button b;

    EditText e,ecard,ecvv,edate;

    SharedPreferences sh;

    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e=(EditText)findViewById(R.id.editText1);

        ecard=(EditText)findViewById(R.id.editText2);
        ecvv=(EditText)findViewById(R.id.editText3);
        edate=(EditText)findViewById(R.id.editText4);

        e.setText("250");
        e.setEnabled(false);
        edate.setOnClickListener(this);
        b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // TODO Auto-generated method stub
                    amt = e.getText().toString();
                    card = ecard.getText().toString();
                    cvv = ecvv.getText().toString();
                    dt = edate.getText().toString();

                    if (card.equalsIgnoreCase("")) {

                        ecard.setError("enter card number");
                        ecard.setFocusable(true);

                    } else if (card.length() != 16) {

                        ecard.setError("enter valid card number(16 digit)");
                        ecard.setFocusable(true);

                    } else if (cvv.equalsIgnoreCase("")) {

                        ecvv.setError("enter your cvv");
                        ecvv.setFocusable(true);

                    } else if (cvv.length() != 3) {

                        ecvv.setError("enter valid cvv (3 digit)");
                        ecvv.setFocusable(true);

                    } else if (dt.equalsIgnoreCase("")) {

                        edate.setError("enter valid date");
                        edate.setFocusable(true);

                    } else {
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Payment.this;
                        String q = "/makepayment?amout=" + amt + "&userid=" + sh.getString("login_id", "") + "&appointmentid=" + User_view_appointment.appointmentid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);
                    }
                }

        });

    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edate.setText((monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("method",status);

            if (status.equalsIgnoreCase("success")){
                Toast.makeText(this, "PAYMENT successfully ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    }