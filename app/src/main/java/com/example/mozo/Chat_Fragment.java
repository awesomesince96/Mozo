package com.example.mozo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Chat_Fragment extends Fragment {

    View view;
    TextView textView;
    private GoogleSignInClient mGoogleSignInClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_fragment,null);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);
        textView = view.findViewById(R.id.textViewChat);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
        String email = account.getEmail();
        textView.setText("Welcome,\n"+email);
        return view;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.logoutButton:
//                mGoogleSignInClient.signOut();
//                Intent intent = new Intent(getActivity().getApplicationContext(),AuthActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
}
