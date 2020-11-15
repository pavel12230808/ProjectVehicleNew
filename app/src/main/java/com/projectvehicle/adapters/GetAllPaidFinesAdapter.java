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
import com.projectvehicle.models.PaymentDuePojo;

import java.util.List;


public class GetAllPaidFinesAdapter extends BaseAdapter {
    List<PaymentDuePojo> ar;
    Context cnt;

    public GetAllPaidFinesAdapter(List<PaymentDuePojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.row_get_paid_fine_dretails, null);


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

        Typeface custom_font = Typeface.createFromAsset(cnt.getAssets(), "fonts/Lato-Medium.ttf");
        tv_title.setTypeface(custom_font);
        tv_desc.setTypeface(custom_font);
        tv_fee.setTypeface(custom_font);
        tv_num_plate.setTypeface(custom_font);





        return obj2;
    }

    /*ProgressDialog pd;
    public  void submitdata(String uname)
    {
        pd= new ProgressDialog(cnt);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.deleteAuthority(uname);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                //Toast.makeText(AddAuthorityActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }*/

}