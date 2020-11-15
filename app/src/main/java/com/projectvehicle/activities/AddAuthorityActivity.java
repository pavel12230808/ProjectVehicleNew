package com.projectvehicle.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddAuthorityActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    EditText et_name,et_emailid,et_phone_no,et_uname,et_password;
    Button bt_reg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private static final String TAG = AddMyCarActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://cegepclm.com/";
    private Uri uri;

    Button btn_season_img,btn_Submit;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    String session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_authority);
        getSupportActionBar().setTitle("Add Authority");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name=(EditText)findViewById(R.id.et_name);
        et_emailid=(EditText)findViewById(R.id.et_emailid);
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_uname=(EditText)findViewById(R.id.et_uname);
        et_password=(EditText)findViewById(R.id.et_password);


        btn_season_img = (Button) findViewById(R.id.btn_season_img);
        btn_season_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

            }
        });

        bt_reg=(Button)findViewById(R.id.bt_reg);
       // btn_Submit = (Button) findViewById(R.id.btn_Submit);
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (et_emailid.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!et_emailid.getText().toString().matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid emailID.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().length()!=10) {
                    Toast.makeText(getApplicationContext(), "Please enter 10 digit Phone Number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadImageToServer();

            }
        });



//        bt_reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (et_name.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Name Should not be Empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//
//                if (et_emailid.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Email Should not be Empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (!et_emailid.getText().toString().matches(emailPattern))
//                {
//                    Toast.makeText(getApplicationContext(), "Please enter valid emailID.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (et_phone_no.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Phone Number Should not be Empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (et_phone_no.getText().toString().length()!=10) {
//                    Toast.makeText(getApplicationContext(), "Please enter 10 digit Phone Number.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (et_password.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                submitdata();
//            }
//        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddAuthorityActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, AddAuthorityActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, AddAuthorityActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadImageToServer(){
        pd=new ProgressDialog(AddAuthorityActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();

//        et_name.getText().toString(),et_emailid.getText().toString(),et_phone_no.getText().toString(),et_password.getText().toString()
        map.put("name",et_name.getText().toString());
        map.put("emailid",et_emailid.getText().toString());
        map.put("phno",et_phone_no.getText().toString());
        map.put("pwd",et_password.getText().toString());


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointUrl uploadImage = retrofit.create(EndPointUrl.class);
        Call<ResponseData> fileUpload = uploadImage.addAuthority(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(AddAuthorityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddAuthorityActivity.this,AddAuthorityActivity.class));
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(AddAuthorityActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    public  void submitdata()
//    {
//        pd= new ProgressDialog(AddAuthorityActivity.this);
//        pd.setTitle("Please wait,Data is being submit...");
//        pd.show();
//        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
//        Call<ResponseData> call = apiService.authorityRegistration(et_name.getText().toString(),et_emailid.getText().toString(),et_phone_no.getText().toString(),et_password.getText().toString());
//        call.enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                pd.dismiss();
//                if (response.body().status.equals("true")) {
//                    Toast.makeText(AddAuthorityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
//                    Log.i("msg", "" + response.body().message);
//                    finish();
//                } else {
//                    Toast.makeText(AddAuthorityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//                Toast.makeText(AddAuthorityActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                pd.dismiss();
//            }
//        });
//    }
}
