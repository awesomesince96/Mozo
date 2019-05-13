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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class Profile_Fragment extends Fragment {

    SharedPreferences sharedPreferences;
    User user;
    ImageView profile_pic;
    Button logoutBtn;
    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        profile_pic = rootView.findViewById(R.id.iv_profile_fragment_profilepic);
        logoutBtn = rootView.findViewById(R.id.logoutButtonPF);
        loadUser();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(),AuthActivity.class);
                startActivity(intent);
            }
        });
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
                Picasso.get().load(url).into(profile_pic);
            }
        }
    }

}