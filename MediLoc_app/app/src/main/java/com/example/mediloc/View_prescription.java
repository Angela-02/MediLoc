package com.example.mediloc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class View_prescription extends AppCompatActivity implements JsonResponse{
    ListView l1;

    String [] prescription,medicine,date,value;


    public  static  String prescription_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_prescription);

        l1=(ListView) findViewById(R.id.view_prescriptions);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prescription_id=prescription[position];
                final  CharSequence[] items ={"Add Review","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(View_prescription.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Add Review")){
                            Intent il=new Intent(getApplicationContext(),Add_Review.class);
                            startActivity(il);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();

                        }
                    }
                });
                builder.show();
            }});



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_prescription.this;
        String q = "/viewprescription?appid=" + User_view_appointment.appointmentid;
        q = q.replace(" ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            String method= jo.getString("method");

            Log.d("method",method);

            if (method.equalsIgnoreCase("viewprescription")){

                if (status.equalsIgnoreCase("success")){

                    JSONArray ja=(JSONArray) jo.getJSONArray("data");


                    prescription=new String[ja.length()];
                    medicine=new String[ja.length()];
                    date =new  String[ja.length()];
                    value=new String[ja.length()];


                    for (int i=0;i< ja.length();i++){
                        prescription[i]=ja.getJSONObject(i).getString("prescription_id");

                        medicine[i]=ja.getJSONObject(i).getString("medicine_details");

                        date[i]=ja.getJSONObject(i).getString("date");

                        value[i]= "   Medicine Details: " + medicine[i] + "\n \n   Date: " + date[i];

                    }
//                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
//                    l1.setAdapter(ar);

                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), R.layout.custlistviewpresc,value);
                    l1.setAdapter(ar);


                }else {
                    Toast.makeText(this, "no messages", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}