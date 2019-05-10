package com.example.mozo;

import android.app.VoiceInteractor;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Signup_1 extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText username;
    private RadioGroup gender;
    private DatePicker dob;
    private Button next;
    private String id;
    private Boolean exist = true;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");

    private FirebaseAuth mAuth;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_1);

        name = findViewById(R.id.et_signup_name);
        username = findViewById(R.id.et_signup_username);
        gender = findViewById(R.id.signup_radio_group);
        dob = findViewById(R.id.dp_signup);
        next = findViewById(R.id.btn_signup_next);

        next.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        user.setName(currentUser.getDisplayName());
        user.setEmail(currentUser.getEmail());
        name.setText(user.getName());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup_next:
                int age = setModel();
                if (age > 17) {
                    if (checkUsername()) {
                        saveUsername();
                        Intent intent = new Intent(Signup_1.this, Signup_2.class);
                        intent.putExtra("user", user);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Username already exist", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    private void saveUsername() {
//        String url = "http://192.168.0.8:8080/api/saveUsername";
        String url = "http://10.0.0.52:8080/api/saveUsername";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", id);
            jsonBody.put("username", user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private boolean checkUsername() {
//        String url = "http://192.168.0.8:8080/api/checkUsername";
        String url = "http://10.0.0.52:8080/api/checkUsername";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("MYTAG", jsonBody.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("MYTAG", response.toString());
                        try {
                            if (response.get("result").toString().equals("true")) {
                                exist = true;
                                Log.e("MYTAG",response.get("result").toString()+"user exitst");
                            } else {
                                exist = false;
                                Log.e("MYTAG",response.get("result").toString()+"user doesnt exist");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        if (exist) {
            return false;
        } else {
            return true;
        }
    }

    private int setModel() {

        user.setUsername(username.getText().toString());
        int selectedRadioButtonID = gender.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);
        String selectedRadioButtonText = selectedRadioButton.getText().toString();
        switch (selectedRadioButtonText) {
            case "Female":
                user.setGender("0");
                break;
            case "Male":
                user.setGender("1");
                break;
            case "Other":
                user.setGender("3");
                break;
        }

        int age = getAge(dob.getYear(), dob.getMonth(), dob.getDayOfMonth());
        if (age > 18) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, dob.getYear());
            cal.set(Calendar.MONTH, dob.getMonth());
            cal.set(Calendar.DAY_OF_MONTH, dob.getDayOfMonth());
            Date dateOfBirth = cal.getTime();
            String birth_date = dateFormatter.format(dateOfBirth);
            user.setdob(birth_date);
            user.setAge(String.valueOf(age));
            Log.e("MYTAG", user.toString());
        } else {
            Toast.makeText(getApplicationContext(), "Age of the user should be greater than 18", Toast.LENGTH_SHORT).show();
        }

        return age;


    }

    private int getAge(int year, int month, int day) {
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dateOfBirth.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    @Override
    public void onBackPressed() {
    }
}
