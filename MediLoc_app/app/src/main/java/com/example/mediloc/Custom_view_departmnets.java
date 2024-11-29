package com.example.mediloc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class Custom_view_departmnets extends ArrayAdapter<String>{

    private Activity context;
    private SharedPreferences sh;
    private String[] department_name;

    public static String medicine;
    private int count = 0;

    public Custom_view_departmnets(Activity context, String[] department_name) {
        super(context, R.layout.activity_custom_view_departments, department_name);
        this.context = context;
        this.department_name = department_name;;
        this.sh = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Override getView() method
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_view_departments, null, true);

//        TextView t = listViewItem.findViewById(R.id.time);
        TextView t1 = listViewItem.findViewById(R.id.departnames);
//        TextView t2 = listViewItem.findViewById(R.id.qualification);
//        TextView t3 = listViewItem.findViewById(R.id.email);

//        t.setText(start_time[position] +"--"+end_time[position]);
          t1.setText(department_name[position]);
//        t3.setText(department_name[position]);
//        t2.setText(qualification[position]);






        String pth = "http://" + sh.getString("ip", "") + "/" + department_name[position];
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
