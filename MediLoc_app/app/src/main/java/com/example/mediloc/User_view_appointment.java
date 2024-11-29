package com.example.mediloc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_appointment extends AppCompatActivity implements JsonResponse{
    ListView l1;

    String []  appointment_id,user_id,description,date,stt,value;

    public  static  String appointmentid;
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_view_appointment);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView) findViewById(R.id.view_appointments);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appointmentid=appointment_id[position];

                if (stt[position].equals("pending")) {

                    final CharSequence[] items = {"Make Payment", "Cancel"};
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(User_view_appointment.this);
                    builder.setTitle("Select Option!");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Make Payment")) {
                                Intent il = new Intent(getApplicationContext(), Payment.class);
                                startActivity(il);

                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();

                            }
                        }
                    });
                    builder.show();

                } else if (stt[position].equals("prescription Added")) {
                    final CharSequence[] items = {"View prescription","Cancel"};
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(User_view_appointment.this);
                    builder.setTitle("Select Option!");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("View prescription")) {
                                Intent il = new Intent(getApplicationContext(), View_prescription.class);
                                startActivity(il);

                            } else if (items[item].equals("Cancel")) {
                                dialog.dismiss();

                            }
                        }
                    });
                    builder.show();

                    
                }else {
                    Toast.makeText(User_view_appointment.this, "Prescription will be added...", Toast.LENGTH_SHORT).show();

                }

            }});



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_view_appointment.this;
        String q = "/User_view_appointments?user_id="+ sh.getString("login_id", "") ;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            String method= jo.getString("method");

            Log.d("method",method);

            if (method.equalsIgnoreCase("viewappointment")){

                if (status.equalsIgnoreCase("success")){

                    JSONArray ja=(JSONArray) jo.getJSONArray("data");

                    appointment_id=new String[ja.length()];
                    user_id=new String[ja.length()];
                    description=new String[ja.length()];
                    stt=new String[ja.length()];
                    date=new String[ja.length()];
                    value=new String[ja.length()];


                    for (int i=0;i< ja.length();i++){
                        appointment_id[i]=ja.getJSONObject(i).getString("appointment_id");
                        user_id[i]=ja.getJSONObject(i).getString("user_id");
                        description[i]=ja.getJSONObject(i).getString("description");
                        stt[i]=ja.getJSONObject(i).getString("status");
                        date[i]=ja.getJSONObject(i).getString("date");


                        value[i]="  STATUS: " + stt[i] + "\n  DATE: " + date[i]  + "\n  DESCRIPTION: " + description[i];

                    }
//                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
//                    l1.setAdapter(ar);

                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), R.layout.custlistviewapp,value);
                    l1.setAdapter(ar);


                }else {
                    Toast.makeText(this, "You No Appointments messages", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}