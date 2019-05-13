package com.example.mozo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TimeUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;


public class APIServices {

    private Boolean exist = true;
    Gson gson = new Gson();

    public Boolean checkUsername(Context context, User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Globals.url);
        stringBuilder.append("api/checkUsername");
        String url = stringBuilder.toString();
        Log.e("MYTAG",url);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", user.getUsername());
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
        if(exist){
            return false;
        }else{
            return true;
        }
    }

    public void saveUsername(Context context,String id, User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Globals.url);
        stringBuilder.append("api/saveUsername");
        String url = stringBuilder.toString();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", id);
            jsonBody.put("username", user.getUsername());
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

    public void createUser(Context context,String id, User user) {
//        String url = "http://192.168.0.8:8080/api/saveUser";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Globals.url);
        stringBuilder.append("api/saveUser");
        String url = stringBuilder.toString();
        JSONObject jsonBody = new JSONObject();
        String userjson = gson.toJson(user);
        try {
            jsonBody.put("id", id);
            jsonBody.put("user", userjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("MYTAG", "___FINAL__");
        Log.e("MYTAG", jsonBody.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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

    public void getPeople(Context context,String id, User user) {
//        String url = "http://192.168.0.8:8080/api/saveUser";
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
        Log.e("MYTAG", "___FINAL__");
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

    public void getUserProfile(final Context context, final String id){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Globals.url);
        stringBuilder.append("api/get");
        String url = stringBuilder.toString();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", id);
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
                        User user = gson.fromJson(response.toString(), User.class);
                        SharedPreferences sharedPreferences = context.getSharedPreferences("s_p",MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                        String user_json = gson.toJson(user);
                        prefsEditor.putString("user", user_json);
                        prefsEditor.putString("id",id);
                        prefsEditor.apply();
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



//    public boolean checkUsername1(Context context, User user) throws InterruptedException {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(Globals.url);
//        stringBuilder.append("/api/checkUsername");
//        String url = stringBuilder.toString();
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("username", user.getUsername());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Boolean exist = true;
//        Log.e("MYTAG", jsonBody.toString());
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//        JsonObjectRequest request = new JsonObjectRequest(url, jsonBody, future, future);
//        requestQueue.add(request);
//        try {
//            JSONObject response = future.get(5, SECONDS); // this will block
//            Log.e("MYTAG", response.toString());
//            if (response.get("result").toString().equals("true")) {
//                exist = true;
//                Log.e("MYTAG",response.get("result").toString()+"user exitst");
//            } else {
//                exist = false;
//                Log.e("MYTAG",response.get("result").toString()+"user doesnt exist");
//            }
//
//        } catch (InterruptedException | JSONException | TimeoutException e) {
//            Log.e("MYTAG",e.toString());
//        } catch (ExecutionException e) {
//            Log.e("MYTAG",e.toString());
//        }
//        Thread.sleep(1000);
//        if(exist){
//            return false;
//        }else{
//            return true;
//        }
//    }

}
