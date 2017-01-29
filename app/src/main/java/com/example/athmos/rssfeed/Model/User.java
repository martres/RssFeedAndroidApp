package com.example.athmos.rssfeed.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by athmos on 26/01/17.
 */

public class User {
    @SerializedName("id")
    public String      id;
    @SerializedName("token")
    public String      token;
    @SerializedName("email")
    public String      email;
    @SerializedName("message")
    public String      message;
    @SerializedName("status")
    public String      status;

}
