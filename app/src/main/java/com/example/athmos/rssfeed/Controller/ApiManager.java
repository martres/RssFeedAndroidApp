package com.example.athmos.rssfeed.Controller;

import android.content.Context;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.athmos.rssfeed.Model.Feed;
import com.example.athmos.rssfeed.Model.Login;
import com.example.athmos.rssfeed.Model.User;
import com.example.athmos.rssfeed.View.AppActivity;
import com.example.athmos.rssfeed.View.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by athmos on 26/01/17.
 */

public class ApiManager {
    private static ApiManager       mInstance = null;
    private static Context          mCtx;
    private Gson                    gson;
    private RequestQueue            listRequestApi;
    private String                  urlServer = "https://gentle-forest-84146.herokuapp.com/";
    private Singleton               singleton;

    private                         ApiManager(Context context)
    {
        mCtx = context;
        gson = new GsonBuilder().create();
        listRequestApi = Volley.newRequestQueue(context.getApplicationContext());
        singleton = Singleton.getInstance();
    }
    public static synchronized ApiManager getInstance(Context context)
    {
        if (mInstance == null) {
            mInstance = new ApiManager(context);
        }
        return mInstance;
    }
    public void                     addUser(final MainActivity activity, final String email, final String password)
    {
        final Login login = new Login();
        login.email = email;
        login.password = password;
        if (email != null && !email.isEmpty())
            listRequestApi.add(new StringRequest(Request.Method.POST, urlServer + "accountRss/create",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            User user = (new Gson()).fromJson(response, new TypeToken<User>(){}.getType());
                            singleton.setUser(user);
                            System.out.println(login.email);
                            System.out.println(login.password);
                            activity.onResponseAddUser(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null )
                                System.out.println("Error code :" + networkResponse.statusCode);
                            System.out.println(gson.toJson(login));
                            System.out.println(login.password);
                            System.out.println("error HTTP: " + error.getMessage());
                            activity.onResponseAddUser(false);
                        }

                    })
            {
                @Override
                public byte[] getBody() {
                    Log.d("GET BODY", gson.toJson(login).getBytes().toString());
                    String JSON = "email=" + email + "&password=" + password;
                    return JSON.getBytes();
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headerLocal = new HashMap<>();
                    headerLocal.put("Content-Type", "application/x-www-form-urlencoded");
                    return headerLocal;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            });
    }

    public void getUser(final MainActivity activity, final String email, final String password) {
        final Login login = new Login();
        login.email = email;
        login.password = password;
        if (email != null && !email.isEmpty())
            listRequestApi.add(new StringRequest(Request.Method.POST, urlServer + "login/mailLogin",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            User user = (new Gson()).fromJson(response, new TypeToken<User>(){}.getType());
                            singleton.setUser(user);
                            activity.onResponseAddUser(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null )
                                System.out.println("Error code :" + networkResponse.statusCode);
                            System.out.println("error HTTP: " + error.getMessage());
                            activity.onResponseAddUser(false);
                        }

                    })
            {
                @Override
                public byte[] getBody() {
                    Log.d("GET BODY", gson.toJson(login).getBytes().toString());
                    String JSON = "email=" + email + "&password=" + password;
                    return JSON.getBytes();
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headerLocal = new HashMap<>();
                    headerLocal.put("Content-Type", "application/x-www-form-urlencoded");
                    return headerLocal;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            });
    }


    public void                     addLink(final AppActivity activity, final String link)
    {
            listRequestApi.add(new StringRequest(Request.Method.POST, urlServer + "feed/addFeeds",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("OKKK ADD LINK");
                            activity.onResponseAddLink(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null )
                                System.out.println("Error code :" + networkResponse.statusCode);
                            System.out.println("error HTTP: " + error.getMessage());
                            activity.onResponseAddLink(false);
                        }

                    })
            {
                @Override
                public byte[] getBody() {
                    String JSON = "feedUrl=" + link;
                    return JSON.getBytes();
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headerLocal = new HashMap<>();
                    headerLocal.put("token", singleton.getUser().token);
                    headerLocal.put("Content-Type", "application/x-www-form-urlencoded");
                    return headerLocal;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            });
    }


    public void getFlux(final AppActivity appActivity) {
        listRequestApi.add(new StringRequest(Request.Method.GET, urlServer + "feed/getFeeds",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("je suis dans Add Feed : " + response);
                        JsonObject feedList = gson.fromJson(response, JsonObject.class);
                        List<Feed> flux = gson.fromJson(feedList.getAsJsonObject("feedList").getAsJsonArray("feedList"), new TypeToken<ArrayList<Feed>>(){}.getType());
                        appActivity.onResponseGetFlux(flux);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null )
                    System.out.println("Error code :" + networkResponse.statusCode);
                System.out.println("error HTTP: " + error.getMessage());
                appActivity.onResponseGetFlux(null);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headerLocal = new HashMap<>();
                headerLocal.put("token", singleton.getUser().token);
                return headerLocal;
            }
        });
    }

    public void getRSSContent(Feed lien, final AppActivity appActivity) {
        listRequestApi.add(new StringRequest(Request.Method.GET, lien.getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        appActivity.parseRssContent(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null )
                    System.out.println("Error code :" + networkResponse.statusCode);
                System.out.println("error HTTP: " + error.getMessage());
                appActivity.parseRssContent("");
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headerLocal = new HashMap<>();
                headerLocal.put("Content-Type", "application/json");
                return headerLocal;
            }
        });
    }
}
