package com.example.mozo;

import android.content.Intent;
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

public class Signup_2 extends AppCompatActivity implements View.OnClickListener {

    private RangeBar rangeBar;
    private RadioGroup gender;
    private RangeBar range;
    private Button next;
    private String id;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_2);

        gender = findViewById(R.id.signup_gender_preference_radio_group);
        rangeBar = findViewById(R.id.signup_age_bar);
        range = findViewById(R.id.signup_distance_bar);
        next = findViewById(R.id.btn_signup2_next);

        next.setOnClickListener(this);

//        range = findViewById(R.id.signup_distance_bar);

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup2_next:
                setModel();
                createUser();
                break;
        }
    }

    private void createUser() {
//        String url = "http://192.168.0.8:8080/api/saveUser";
        String url = "http://10.0.0.52:8080/api/saveUser";
        JSONObject jsonBody = new JSONObject();
        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        String userjson = gson.toJson(user);
        Log.e("MYTAG","GSON"+gson.toJson(user));
        Log.e("MYTAG","JACKSON"+jsonStr);
        try {
            jsonBody.put("id", id);
            jsonBody.put("user", userjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("MYTAG", "___FINAL__");
        Log.e("MYTAG", jsonBody.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MYTAG", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
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


    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


}
