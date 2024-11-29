package com.example.mediloc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

//import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Custom_view_doctors extends ArrayAdapter<String>{

    private Activity context;
    private SharedPreferences sh;
    private String[] first_name, last_name, start_time,end_time,email,qualification;

    public static String medicine;
    private int count = 0;

    public Custom_view_doctors(Activity context, String[] first_name, String[] last_name, String[] start_time,String[] end_time,String[] email,String[] qualification) {
        super(context, R.layout.activity_custom_view_doctors, first_name);
        this.context = context;
        this.first_name = first_name;
        this.last_name = last_name;
        this.start_time = start_time;
        this.end_time=end_time;
        this.email=email;
        this.qualification=qualification;
        this.sh = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Override getView() method
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_view_doctors, null, true);

        TextView t = listViewItem.findViewById(R.id.time);
        TextView t1 = listViewItem.findViewById(R.id.name);
        TextView t2 = listViewItem.findViewById(R.id.qualification);
        TextView t3 = listViewItem.findViewById(R.id.email);

        t.setText(start_time[position] +"--"+end_time[position]);
        t1.setText(first_name[position]+" "+last_name[position]);
        t3.setText(email[position]);
        t2.setText(qualification[position]);






        String pth = "http://" + sh.getString("ip", "") + "/" + first_name[position];
        pth = pth.replace("~", "");
        Log.d("-------------", pth);

//        Picasso.with(context)
//                .load(pth)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//                .into(img1);

        return listViewItem;
    }

    private void displayCount(TextView messageTextView) {
        messageTextView.setText(String.valueOf(count));
        SharedPreferences.Editor e = sh.edit();
        e.putString("count", String.valueOf(count));
        e.apply();
    }



    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
