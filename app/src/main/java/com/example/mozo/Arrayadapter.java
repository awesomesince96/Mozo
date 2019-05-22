package com.example.mozo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Arrayadapter extends ArrayAdapter<User> {

    Context context;

    public Arrayadapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    public View getView (int position, View convertView, ViewGroup parent){
        User user_item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.image_name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_swipe);
        String url = "https://firebasestorage.googleapis.com/v0/b/mozo-1ddd9.appspot.com/o/nHGSWUJKzvXIyPuKr43Qb6nkMmZ2%2F1.jpg?alt=media&token=cd7b07a4-000e-43f1-938e-8d01c2aa443b";
        name.setText(user_item.getName());
        if(user_item.getImage_url()!=null){
            Picasso.get().load(user_item.getImage_url().get(0)).into(image);
        }else{
            Picasso.get().load(R.mipmap.ic_launcher).into(image);
        }


        return  convertView;

    }


}
