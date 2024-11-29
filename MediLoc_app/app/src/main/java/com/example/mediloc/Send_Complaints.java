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

public class Send_Complaints extends AppCompatActivity implements JsonResponse{

    EditText e1;
    Button b1;

    String []  complaints,complaints_id,reply,date,value;
    String Complaints;

    SharedPreferences sh;

    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_complaints);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.view_complaints);


        e1=(EditText) findViewById(R.id.editTextText9);
        b1=(Button) findViewById(R.id.button6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Complaints = e1.getText().toString();

                if (Complaints.equalsIgnoreCase("")){
                    e1.setError("Enter the Complaints");
                    e1.setFocusable(true);

                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Send_Complaints.this;
                    String q = "/user_send_complaints?complaints=" + Complaints + "&userid=" + sh.getString("login_id", "");
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }

            }
        });



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Send_Complaints.this;
        String q = "/User_view_complaints?user_id=" + sh.getString("login_id","");
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


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(this, "Send Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Send_Complaints.class));

            }else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();


            }
            } else if (method.equalsIgnoreCase("view")) {
                if (status.equalsIgnoreCase("success")){

                    JSONArray ja=(JSONArray) jo.getJSONArray("data");

                    complaints_id=new String[ja.length()];
                    complaints=new String[ja.length()];
                    reply=new  String[ja.length()];
                    date=new String[ja.length()];
                    value=new String[ja.length()];

                    for (int i=0;i<ja.length();i++){
                        complaints_id[i]=ja.getJSONObject(i).getString("complaint_id");
                        complaints[i]=ja.getJSONObject(i).getString("complaints");
                        reply[i]=ja.getJSONObject(i).getString("reply");
                        date[i]=ja.getJSONObject(i).getString("date");

                        value[i]="    Complaints: "+ complaints[i]+ "\n    Reply: " + reply[i] + "\n    Date: " + date[i];

                    }

                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);




                }
                
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}