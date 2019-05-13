package com.example.mozo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        Picasso.get().load(url).into(image);

        return  convertView;

    }
}
