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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
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
        ((CentralActivity) getActivity())
                .setActionBarTitle("Explore");


//        al = new ArrayList<>();
        rowItems = new ArrayList<User>();

        rowItems.add(new User("Krishna"));
//        rowItems.add(new User("Nitu"));
//        rowItems.add(new User("Ankita"));
//        rowItems.add(new User("Trugrits"));
//        rowItems.add(new User("Enstien"));
//        rowItems.add(new User("Transit"));
//        rowItems.add(new User("Commons"));
        getUsers(getContext(),id,user);

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

//        apiServices = new APIServices();
//        apiServices.getPeople(getContext(), id, user);

    }

    public void getUsers(Context context,String id, User user){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Globals.url);
        stringBuilder.append("api/Explore");
        String url = stringBuilder.toString();

        JSONObject jsonBody = new JSONObject();
        String userjson = gson.toJson(user);
        try {
            jsonBody.put("id", id);
            jsonBody.put("user", userjson);
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
                        rowItems.addAll((Collection<? extends User>) user_wrapper);
                        Log.e("MYTAG",user_wrapper.toString());
                        Log.e("MYTAG",rowItems.toString());
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



