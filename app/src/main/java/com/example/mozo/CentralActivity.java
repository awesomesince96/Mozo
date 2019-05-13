package com.example.mozo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;

public class CentralActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final int REQUEST_PERMISSION=0;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences("s_p",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", null);
        String json_id = sharedPreferences.getString("id", null);
        User obj = gson.fromJson(json, User.class);
        Log.e("MYTAG","PARENT: "+json_id+obj.toString());

    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()){
                case R.id.navigation_nearby:
                    fragment = new Nearby_Fragment();
                    break;
                case R.id.navigation_explore:
                    fragment = new Explore_Fragment();
                    break;
                case R.id.navigation_profile:
                    fragment = new Profile_Fragment();
                    break;
                case R.id.navigation_chat:
                    fragment = new Chat_Fragment();
                    break;

            }

            return loadFragment(fragment);
    }
}
