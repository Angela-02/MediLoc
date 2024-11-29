package com.example.mediloc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_doctors extends AppCompatActivity implements JsonResponse{

    ListView l1;

    String []  department_id,first_name,last_name,qualification,start_time,end_time,date,doctors_id,stt,value,email;

    public  static  String doctorsid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_doctors);

        l1=(ListView) findViewById(R.id.view_doctors);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doctorsid=doctors_id[position];
                final  CharSequence[] items ={"Make Appointment","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(View_doctors.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Make Appointment")){
                            Intent il=new Intent(getApplicationContext(),Make_Appointment.class);
                            startActivity(il);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();

                        }
                    }
                });
                builder.show();
            }});


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_doctors.this;
        String q = "/User_view_doctors?departments_id="+ View_hospital_Departmnets.departmnetid;
        q = q.replace(" ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status =jo.getString("status");

            String method = jo.getString("method");

            Log.d("method",method);

            if (method.equalsIgnoreCase("view")){

                if (status.equalsIgnoreCase("success")){


                    JSONArray ja=(JSONArray) jo.getJSONArray("data");

                    department_id=new String[ja.length()];
                    doctors_id=new String[ja.length()];
                    first_name=new String[ja.length()];
                    last_name=new String[ja.length()];
                    qualification=new String[ja.length()];
                    start_time=new String[ja.length()];
                    end_time=new String[ja.length()];
                    date=new String[ja.length()];
                    email=new String[ja.length()];

                    stt=new String[ja.length()];
                    value=new String[ja.length()];

                    for (int i=0;i<ja.length();i++){

                        department_id[i]=ja.getJSONObject(i).getString("department_id");
                        doctors_id[i]=ja.getJSONObject(i).getString("doctors_id");
                        first_name[i]=ja.getJSONObject(i).getString("first_name");
                        last_name[i]=ja.getJSONObject(i).getString("last_name");
                        qualification[i]=ja.getJSONObject(i).getString("qualification");
                        start_time[i]=ja.getJSONObject(i).getString("start_time");
                        end_time[i]=ja.getJSONObject(i).getString("end_time");
                        email[i]=ja.getJSONObject(i).getString("email");

                        date[i]=ja.getJSONObject(i).getString("date");
                        stt[i]=ja.getJSONObject(i).getString("status");


                        value[i]="Name: " + first_name[i] + "\nQualification: " + qualification[i] + "\nStart Time: " + start_time[i] +  "\nEnd Time: " + end_time[i] +  "\nDate: " + date[i] + "\nStatus: " + stt[i];

                    }

//                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
//                    l1.setAdapter(ar);

                    Custom_view_doctors ar=new Custom_view_doctors(View_doctors.this,first_name,last_name,start_time,end_time,email,qualification);
                    l1.setAdapter(ar);


                }else {
                    Toast.makeText(this, " No doctors are scheduled today. ", Toast.LENGTH_SHORT).show();
                }
            }

            else {
                Toast.makeText(this , "No Messages", Toast.LENGTH_SHORT).show();
            }


        }

        catch (Exception e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("status", e.toString());
        }




    }
}