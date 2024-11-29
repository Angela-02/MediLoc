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

public class View_hospital_Departmnets extends AppCompatActivity implements JsonResponse{

    ListView l1;

    String []  department_id,hospital_id,department_name,value;

    public  static  String hospitalid,departmnetid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_hospital_departmnets);

        l1=(ListView) findViewById(R.id.view_departmnets);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hospitalid=hospital_id[position];
                departmnetid=department_id[position];
                final  CharSequence[] items ={"View Doctors","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(View_hospital_Departmnets.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("View Doctors")){
                            Intent il=new Intent(getApplicationContext(),View_doctors.class);
                            startActivity(il);


                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();

                        }
                    }
                });
                builder.show();
            }});



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_hospital_Departmnets.this;
        String q = "/User_view_hospital_departments?hospital_id="+ View_Hospitals.hospitalid;
        q = q.replace(" ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status =jo.getString("status");


                if (status.equalsIgnoreCase("success")){


                    JSONArray ja=(JSONArray) jo.getJSONArray("data");

                    hospital_id=new String[ja.length()];
                    department_id=new String[ja.length()];
                    department_name=new String[ja.length()];
                    value=new String[ja.length()];

                    for (int i=0;i<ja.length();i++){

                        hospital_id[i]=ja.getJSONObject(i).getString("hospital_id");
                        department_name[i]=ja.getJSONObject(i).getString("department_name");
                        department_id[i]=ja.getJSONObject(i).getString("department_id");


                        value[i]="Department Name: " + department_name[i] ;

                    }

//                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
//                    l1.setAdapter(ar);

                    Custom_view_departmnets ar=new Custom_view_departmnets(View_hospital_Departmnets.this,department_name);
                    l1.setAdapter(ar);

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