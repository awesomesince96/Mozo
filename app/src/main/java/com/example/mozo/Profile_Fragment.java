package com.example.mozo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class Profile_Fragment extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    User user;
    ImageView profile_pic;
    TextView name;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        ((CentralActivity) getActivity())
                .setActionBarTitle("Profile");
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        profile_pic = rootView.findViewById(R.id.iv_profile_fragment_profilepic);
        name = rootView.findViewById(R.id.tv_profile_fragment_name);
        loadUser();
//        return inflater.inflate(R.layout.profile_fragment, null);
        return rootView;
    }

    private void loadUser() {
        sharedPreferences = getActivity().getSharedPreferences("s_p",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", null);
        if( json != null){
            user = gson.fromJson(json,User.class);
            if(user.getImage_url() != null){
               String url =  user.getImage_url().get(0);
               StringBuilder stringBuilder = new StringBuilder();
               stringBuilder.append(user.getName());
               stringBuilder.append(" , ");
               stringBuilder.append(user.getAge());
               name.setText(stringBuilder.toString());
               Picasso.get().load(url).into(profile_pic);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_profile_fragment_editprofile:
                ((CentralActivity) getActivity())
                        .moveToEditProfile();
                break;
//                ((CentralActivity) getActivity()).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container,new ProfileEdit_Fragment())
//                        .commit();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container,new ProfileEdit_Fragment());
//                fragmentTransaction.commit();
        }
    }
}
