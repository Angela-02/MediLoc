package com.example.mediloc;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_Hospitals extends AppCompatActivity implements JsonResponse{

    ListView l1;

    EditText e1;


    String search;

    String []  hospital_name,hospital_id,place,phone,email,value,longitude,latitude;

    public  static String hospitalid,dlongi1,dlati1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_hospitals);

        l1=(ListView) findViewById(R.id.view_hospitals);


        startService(new Intent(getApplicationContext(), LocationService.class));



        e1=(EditText)findViewById(R.id.search) ;

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                search=e1.getText().toString();
//
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) View_Hospitals.this;
                String q = "/searchhospital?&search=" + search ;
                q = q.replace(" ", "%20");
                JR.execute(q);


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hospitalid=hospital_id[position];
                dlati1=latitude[position];
                dlongi1=longitude[position];
                final  CharSequence[] items ={"View Departments","Send Enquiry","view location","Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(View_Hospitals.this);
                builder.setTitle("Select Option!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("View Departments")){
                            Intent il=new Intent(getApplicationContext(), View_hospital_Departmnets.class);
                            startActivity(il);

                        } else if (items[item].equals("Send Enquiry")) {
                            Intent il=new Intent(getApplicationContext(), Send_enquiry.class);
                            startActivity(il);

                        } else if (items[item].equals("view location")) {

                            String url = "http://maps.google.com/maps?q=loc:"+View_Hospitals.dlati1+","+View_Hospitals.dlongi1;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            intent.setPackage("com.google.android.apps.maps"); // Specify package for Google Maps
                            startActivity(intent);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();

                        }
                    }
                });
                builder.show();
            }});


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_Hospitals.this;
        String q = "/User_view_hospitals?lati="+ LocationService.lati+ "&logi="+ LocationService.logi;
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

                    hospital_id=new String[ja.length()];
                    hospital_name=new String[ja.length()];
                    place=new String[ja.length()];
                    phone=new String[ja.length()];
                    email=new String[ja.length()];
                    latitude=new String[ja.length()];
                    longitude=new String[ja.length()];
                    value=new String[ja.length()];

                    for (int i=0;i<ja.length();i++){

                        hospital_id[i]=ja.getJSONObject(i).getString("hospital_id");
                        hospital_name[i]=ja.getJSONObject(i).getString("hospital_name");
                        place[i]=ja.getJSONObject(i).getString("place");
                        phone[i]=ja.getJSONObject(i).getString("phone");
                        email[i]=ja.getJSONObject(i).getString("email");
                        latitude[i]=ja.getJSONObject(i).getString("latitude");
                        longitude[i]=ja.getJSONObject(i).getString("longitude");


                        value[i]="Name: " + hospital_name[i] + "\nPlace: " + place[i] + "\nPhone: " + phone[i] + "\nEmail: " + email[i] ;

                    }

                    ArrayAdapter<String> ar =new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);

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