package com.example.mediloc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class Make_Appointment extends AppCompatActivity implements JsonResponse{

    Button b1;

    EditText e1;

    SharedPreferences sh;


    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_appointment);

        b1=(Button) findViewById(R.id.button7);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText) findViewById(R.id.editTextText10);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description=e1.getText().toString();

                if (description.equalsIgnoreCase("")){
                    e1.setFocusable(true);
                    e1.setError("Enter the Description");

                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Make_Appointment.this;
                    String q = "/user_make_appointment?desc=" + description + "&userid=" + sh.getString("login_id", "") + "&doctors_id=" + View_doctors.doctorsid;
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
                Toast.makeText(this, "Make Appointment  Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}