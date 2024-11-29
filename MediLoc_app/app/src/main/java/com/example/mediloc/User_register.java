package com.example.mediloc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class User_register extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6,e7,e8;

    Button b1;

    RadioGroup r1;

    String fname,lname,place,number,email,age,selectedOption,username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_register);

        startService(new Intent(getApplicationContext(), LocationService.class));

        r1=(RadioGroup) findViewById(R.id.radio_group);

        b1=(Button) findViewById(R.id.button3);


        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                selectedOption = radioButton.getText().toString();

            }
        });

        e1=(EditText) findViewById(R.id.editTextText2);
        e2=(EditText) findViewById(R.id.editTextText3);
        e3=(EditText) findViewById(R.id.editTextText4);
        e4=(EditText) findViewById(R.id.editTextPhone);
        e5=(EditText) findViewById(R.id.editTextTextEmailAddress);
        e6=(EditText) findViewById(R.id.editTextText5);
        e7=(EditText) findViewById(R.id.editTextText6);
        e8=(EditText) findViewById(R.id.editTextText7);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = e1.getText().toString();
                lname =e2.getText().toString();
                place = e3.getText().toString();
                number = e4.getText().toString();
                email = e5.getText().toString();
                age= e6.getText().toString();
                username=e7.getText().toString();
                password=e8.getText().toString();

                if(fname.equalsIgnoreCase("")){
                    e1.setError("Enter the  First Name");
                    e1.setFocusable(true);
                } else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter the Last Name");
                    e2.setFocusable(true);
                    
                } else if (place.equalsIgnoreCase("")) {
                    e3.setFocusable(true);
                    e3.setError("Enter the place");
                    
                } else if (number.equalsIgnoreCase("")) {
                    e4.setError("Enter the Number");
                    e4.setFocusable(true);

                } else if (email.equalsIgnoreCase("")) {
                    e5.setFocusable(true);
                    e5.setError("Enter the Email");

                } else if (age.equalsIgnoreCase("")) {
                    e6.setError("Enter the Age");
                    e6.setFocusable(true);

                } else if (username.equalsIgnoreCase("")) {
                    e7.setFocusable(true);
                    e7.setError("Enter the Username");

                } else if (password.equalsIgnoreCase("")) {
                    e8.setError("Enter the Password");
                    e8.setFocusable(true);

                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) User_register.this;
                    String q = "/user_register?fname=" + fname + "&lname=" + lname + "&gender=" + selectedOption + "&phone=" + number + "&email=" + email + "&place=" + place + "&age=" + age  + "&username=" + username + "&password=" + password + "&latitude=" + LocationService.lati + "&longitude=" + LocationService.logi;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl",status);

            if (status.equalsIgnoreCase("success")){
                Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));

            }else {

                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}