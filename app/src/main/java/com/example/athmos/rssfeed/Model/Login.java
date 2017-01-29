package com.example.athmos.rssfeed.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by athmos on 29/01/17.
 */

public class Login {
    @SerializedName("email")
    public String      email;
    @SerializedName("password")
    public String      password;
}
