package com.example.mediloc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Send_enquiry extends AppCompatActivity implements JsonResponse {

    EditText e1;

    Button b1;

    String enquiry;

    String []  Enquiry,reply,enquiryid,value,date;

    SharedPreferences sh;

    ListView l1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_enquiry);



        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText) findViewById(R.id.editTextText8);

        b1=(Button) findViewById(R.id.button5);
        l1=(ListView)  findViewById(R.id.view_enquiry);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enquiry = e1.getText().toString();

                if (enquiry.equalsIgnoreCase("")){
                    e1.setError("Enter the Enquiry...");
                    e1.setFocusable(true);

                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Send_enquiry.this;
                    String q = "/user_send_enquiry?enquiry=" + enquiry + "&userid=" + sh.getString("login_id", "") + "&hospitalid=" + View_Hospitals.hospitalid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }
            }
        });


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Send_enquiry.this;
        String q = "/User_view_enquiry?user_id=" + sh.getString("login_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        
        try {
            
            String status = jo.getString("status");
            String method = jo.getString("method");

            Log.d("pearl",status);

            if (method.equalsIgnoreCase("send")){

                if (status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "Send Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Send_enquiry.class));
                }else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            } else if (method.equalsIgnoreCase("view")) {

                if (status.equalsIgnoreCase("success")){

                    JSONArray ja=(JSONArray) jo.getJSONArray("data");

                    enquiryid=new String[ja.length()];
                    Enquiry=new String[ja.length()];
                    reply=new  String[ja.length()];
                    date=new  String[ja.length()];
                    value=new String[ja.length()];

                    for (int i=0;i<ja.length();i++){

                        enquiryid[i]=ja.getJSONObject(i).getString("enquiry_id");
                        Enquiry[i]=ja.getJSONObject(i).getString("enquiry");
                        reply[i]=ja.getJSONObject(i).getString("reply");
                        date[i]=ja.getJSONObject(i).getString("date");

                        value[i]="    Complaints: "+ Enquiry[i]+ "\n    Reply: " + reply[i] + "\n    Date: " + date[i];


                    }

                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);

                }

            }


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();


           
        }

    }
}