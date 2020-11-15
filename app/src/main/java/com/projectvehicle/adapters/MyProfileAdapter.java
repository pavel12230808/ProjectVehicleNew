package com.projectvehicle.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.projectvehicle.R;
import com.projectvehicle.models.MyProfilePOJO;


import java.util.List;

public class MyProfileAdapter extends BaseAdapter {
    List<MyProfilePOJO> ar;
    Context cnt;

    public MyProfileAdapter(List<MyProfilePOJO> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.adapter_myprofile, null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(ar.get(pos).getImage()).into(image_view);

        TextView tv_name=(TextView)obj2.findViewById(R.id.tv_name);
        tv_name.setText("Name:  "+ar.get(pos).getName());

        TextView tv_email=(TextView)obj2.findViewById(R.id.tv_email);
        tv_email.setText("Email:  "+ar.get(pos).getEmail());

        Typeface custom_font = Typeface.createFromAsset(cnt.getAssets(), "fonts/Lato-Medium.ttf");
        tv_name.setTypeface(custom_font);
        tv_email.setTypeface(custom_font);


        return obj2;
    }

}
