package com.projectvehicle.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projectvehicle.R;
import com.projectvehicle.activities.MapActivity;
import com.projectvehicle.activities.PayFineActivity;
import com.projectvehicle.models.PaymentDuePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GuestDetailsAdapter extends BaseAdapter {
    List<PaymentDuePojo> ar;
    Context cnt;


    public GuestDetailsAdapter(List<PaymentDuePojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.row_guest, null);


        ImageView img_car=(ImageView)obj2.findViewById(R.id.img_car);
        Glide.with(cnt).load(ar.get(pos).getPic()).into(img_car);

        TextView tv_title=(TextView)obj2.findViewById(R.id.tv_title);
        tv_title.setText("Title:  "+ar.get(pos).getTitle());

        TextView tv_desc=(TextView)obj2.findViewById(R.id.tv_desc);
        tv_desc.setText("Description:  "+ar.get(pos).getDescription());

        TextView tv_fee=(TextView)obj2.findViewById(R.id.tv_fee);
        tv_fee.setText("Fine:  "+ar.get(pos).getFee()+"$");

        TextView tv_num_plate=(TextView)obj2.findViewById(R.id.tv_num_plate);
        tv_num_plate.setText("Number Plate:  "+ar.get(pos).getNumber_plate());

        TextView tv_status=(TextView)obj2.findViewById(R.id.tv_status);
        tv_status.setText("Status:  "+ar.get(pos).getStatus());

        TextView tv_sname=(TextView)obj2.findViewById(R.id.tv_sname);
        tv_sname.setText("Status:  "+ar.get(pos).getEmail());


        Button btn_lag_lat=(Button)obj2.findViewById(R.id.btn_lag_lat);
        btn_lag_lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(cnt, "Latitude"+ar.get(pos).getLat()+"Longitude"+ar.get(pos).getLng(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(cnt, MapActivity.class);
                intent.putExtra("lng",ar.get(pos).getLng());
                intent.putExtra("lat",ar.get(pos).getLat());
                intent.putExtra("sname",ar.get(pos).getSname());
                intent.putExtra("fee",ar.get(pos).getFee());
                cnt.startActivity(intent);

            }
        });

        Button btn_pay=(Button)obj2.findViewById(R.id.btn_pay);

        if (ar.get(pos).getStatus().equals("paid")) {
            btn_pay.setVisibility(View.GONE);
        } else {
            btn_pay.setVisibility(View.VISIBLE);
        }

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, PayFineActivity.class);
                intent.putExtra("id",ar.get(pos).getId());
                intent.putExtra("Title",ar.get(pos).getTitle());
                intent.putExtra("Description",ar.get(pos).getDescription());
                intent.putExtra("Fee",ar.get(pos).getFee());
                intent.putExtra("Num_Plate",ar.get(pos).getNumber_plate());
                cnt.startActivity(intent);
            }
        });


        Typeface custom_font = Typeface.createFromAsset(cnt.getAssets(), "fonts/Lato-Medium.ttf");
        tv_title.setTypeface(custom_font);
        tv_desc.setTypeface(custom_font);
        tv_fee.setTypeface(custom_font);
        tv_num_plate.setTypeface(custom_font);
        tv_status.setTypeface(custom_font);


        return obj2;
    }

}