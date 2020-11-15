package com.projectvehicle.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    EditText et_first_name,et_last_name,et_email,et_phone_no,et_password,et_number_plate;
    Spinner spin_stud_emp;
    Button bt_reg;
    Button select_image;
    RadioButton radioMale,radioFemale;
    String str_gender;
    ProgressDialog progressDialog;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://cegepclm.com/";
    private Uri uri;

    Button btn_season_img,btn_Submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        select_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
//                openGalleryIntent.setType("image/*");
//                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
//            }
//        });

        btn_season_img = (Button) findViewById(R.id.btn_season_img);
        btn_season_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

            }
        });

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f2();
                if (et_first_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "First Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_last_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Last Name Should not be Empty", Toast.LENGTH_SHORT).show();
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

                if (et_email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!et_email.getText().toString().matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid emailID.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (et_number_plate.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Number plate Should not be Empty", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                //uploadImageToServer();
                submitdata();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, RegistrationActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, RegistrationActivity.this);
                file = new File(filePath);

            } else {
                //  EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
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
            String filePath = getRealPathFromURIPath(uri, RegistrationActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }
    ProgressDialog pd;
    private void uploadImageToServer(){
        pd=new ProgressDialog(RegistrationActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("first_name",et_first_name.getText().toString());
        map.put("last_name",et_last_name.getText().toString());
        map.put("emailid",et_email.getText().toString());
        map.put("phonenumber", et_phone_no.getText().toString());
        map.put("pwd",et_password.getText().toString());
//        map.put("utype",spin_stud_emp.getSelectedItem().toString());
//        map.put("gender",str_gender);


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointUrl uploadImage = retrofit.create(EndPointUrl.class);
        Call<ResponseData> fileUpload = uploadImage.user_registration(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, "Registered successfully. ", Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, "Error"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void f2() {

        if (radioMale.isChecked()) {
            str_gender = "M";
        } else {
            str_gender = "F";
        }
    }
    public void initViews(){
        radioFemale=(RadioButton)findViewById(R.id.radioFemale);
        radioMale=(RadioButton)findViewById(R.id.radioMale);
        et_first_name=(EditText)findViewById(R.id.et_first_name);
        et_last_name=(EditText)findViewById(R.id.et_last_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_password=(EditText)findViewById(R.id.et_password);
        et_number_plate=(EditText)findViewById(R.id.et_number_plate);

        bt_reg=(Button)findViewById(R.id.bt_reg);
        select_image=(Button)findViewById(R.id.select_image);

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

    public  void submitdata()
    {
        pd= new ProgressDialog(RegistrationActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.userRegistration(et_first_name.getText().toString(),et_last_name.getText().toString(),et_phone_no.getText().toString(),et_email.getText().toString(),et_password.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
}