package com.example.mediloc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse{
    Button b1;

    TextView t1;
    EditText e1,e2;

    public  static  String username,password,logid,usertype;

    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText) findViewById(R.id.editTextText);
        e2=(EditText) findViewById(R.id.editTextTextPassword);

        b1=(Button) findViewById(R.id.button2);
        t1=(TextView) findViewById(R.id.button4);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username= e1.getText().toString();
                password = e2.getText().toString();

                if (username.equalsIgnoreCase("")){
                    e1.setError("Enter Your User Name");
                    e1.setFocusable(true);

                } else if (password.equalsIgnoreCase("")) {
                    e2.setError("Enter the Password");
                    e2.setFocusable(true);
                    
                }else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Login.this;
                    String q = "/user_login?username=" + username + "&password=" + password;
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
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                logid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("user_type");


                SharedPreferences.Editor e =sh.edit();
                e.putString("login_id",logid);
                e.commit();


                if (usertype.equals("user")){
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User_home.class));
                }else{
                    Toast.makeText(this, "Login Failed invalid Username or password", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }

            }

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    public void onBackPressed ()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(b);
    }

}