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
        getUsers(getContext());
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.image_name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_swipe);
        String url = "https://firebasestorage.googleapis.com/v0/b/mozo-1ddd9.appspot.com/o/nHGSWUJKzvXIyPuKr43Qb6nkMmZ2%2F1.jpg?alt=media&token=cd7b07a4-000e-43f1-938e-8d01c2aa443b";
        name.setText(user_item.getName());
        Picasso.get().load(user_item.getImage_url().get(0)).placeholder(R.mipmap.pic).into(image);

        return  convertView;

    }

    public void getUsers(Context context){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Globals.url);
        stringBuilder.append("api/Explore");
        String url = stringBuilder.toString();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("field", "gender");
            jsonBody.put("value", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("MYTAG", jsonBody.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("MYTAG",response.toString());
                        Gson gson = new Gson();
                        User_Wrapper user_wrapper = gson.fromJson(response.toString(), User_Wrapper.class);
                        Log.e("MYTAG",user_wrapper.toString());
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
}
