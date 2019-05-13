package com.example.mozo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Explore_Fragment extends Fragment {

    private ArrayList<String> al;
//    private ArrayAdapter<String> arrayAdapter;
    private int i;

    private User user_cards[];
    ListView listView;
    List<User> rowItems;
    private Arrayadapter arrayAdapter;
    APIServices apiServices;
    User user;
    String id;
    SharedPreferences sharedPreferences;
    Gson gson = new Gson();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.explore_fragment, null);

        getPeople();



//        al = new ArrayList<>();
        rowItems = new ArrayList<User>();

        rowItems.add(new User("Krishna"));
        rowItems.add(new User("Nitu"));
        rowItems.add(new User("Ankita"));
        rowItems.add(new User("Trugrits"));
        rowItems.add(new User("Enstien"));
        rowItems.add(new User("Transit"));
        rowItems.add(new User("Commons"));

//        al.add("php");
//        al.add("c");
//        al.add("python");
//        al.add("java");
//        al.add("html");
//        al.add("c++");
//        al.add("css");
//        al.add("javascript");

//        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.items, R.id.image_name, al);
        arrayAdapter = new Arrayadapter(getContext(), R.layout.items, rowItems);

//        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
//                al.remove(0);
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(getActivity(), "Nope", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(getActivity(), "Like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                rowItems.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
               /* View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);*/
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT);
            }
        });


        return view;
    }

    private void getPeople() {
        sharedPreferences = getActivity().getSharedPreferences("s_p",MODE_PRIVATE);
        Log.e("MYTAG","Sharred Preference get");
        String user_json = sharedPreferences.getString("user","");
        Log.e("MYTAG",user_json);
        user = gson.fromJson(user_json,User.class);
        Log.e("MYTAG",user.toString());
        id = sharedPreferences.getString("id","");
        Log.e("MYTAG",id);

        apiServices = new APIServices();
        apiServices.getPeople(getContext(), id, user);
    }

}



