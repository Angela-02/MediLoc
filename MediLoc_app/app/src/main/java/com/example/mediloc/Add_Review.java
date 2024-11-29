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

public class Add_Review extends AppCompatActivity implements JsonResponse{

    EditText e1;

    Button b1;

    String  review;

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText) findViewById(R.id.editTextText11);
        b1=(Button) findViewById(R.id.button10);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                review = e1.getText().toString();

                if (review.equalsIgnoreCase("")){
                    e1.setError("Enter the Review");
                    e1.setFocusable(true);

                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Add_Review.this;
                    String q = "/User_add_review?user_id=" + sh.getString("login_id","")  + "&prescription_id=" + View_prescription.prescription_id + "&review=" + review;
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
            Log.d("Pearl",status);

            if (status.equalsIgnoreCase("success")){
                Toast.makeText(this, "Add Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), User_home.class));
            }else {
                Toast.makeText(this, "Send Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}