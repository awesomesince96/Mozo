package com.example.mozo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appyvet.materialrangebar.RangeBar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Signup_2 extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private RangeBar rangeBar;
    private RadioGroup gender;
    private RangeBar range;
    private Button next;
    private String id;
    public static Context context;
    private User user;
    APIServices apiServices = new APIServices();
    LocationManager locationManager;
    LocationListener locationListener;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_2);

        gender = findViewById(R.id.signup_gender_preference_radio_group);
        rangeBar = findViewById(R.id.signup_age_bar);
        range = findViewById(R.id.signup_distance_bar);
        next = findViewById(R.id.btn_signup2_next);
        next.setOnClickListener(this);
        context = getApplicationContext();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Toast.makeText(getApplicationContext(), "Please Turn On Your Location", Toast.LENGTH_LONG).show();
        sharedPreferences = getSharedPreferences("s_p",MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();

        getLocation();

        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                user.setMin_age(leftPinValue);
                user.setMax_age(rightPinValue);
            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {

            }
        });

        range.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                user.setMax_range(rightPinValue);
            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {

            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,     0, this);
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup2_next:
                setModel();
//                if(user.getLat() != null){
                    user.setPagination("1");
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    prefsEditor.putString("user", json);
                    prefsEditor.putString("id",id);
                    prefsEditor.apply();
                    apiServices.createUser(context,id,user);
                    Intent intent = new Intent(Signup_2.this, CentralActivity.class);
                    startActivity(intent);
//                }else{
//                    Toast.makeText(getApplicationContext(), "Please Turn On Your Location", Toast.LENGTH_LONG).show();
//                }

                break;
        }
    }



    private void setModel() {
//        user.setRange(String.valueOf(range.getProgress()));
        int selectedRadioButtonID = gender.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);
        String selectedRadioButtonText = selectedRadioButton.getText().toString();
        switch (selectedRadioButtonText){
            case "Female": user.setInterested_gender("0"); break;
            case "Male": user.setInterested_gender("1"); break;
            case "Other": user.setInterested_gender("3"); break;
        }
        Log.e("MYTAG",user.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        id = (String) intent.getSerializableExtra("id");
        Log.e("MYTAG",user.toString());
        user.setMax_range("100");
        user.setMin_age("18");
        user.setMax_age("55");
        user.setStatus("1");



    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            user.setLat(String.valueOf(location.getLatitude()));
            user.setLng(String.valueOf(location.getLongitude()));
            Log.e("MYTAG", location.getLatitude() + " and " + location.getLongitude());
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
