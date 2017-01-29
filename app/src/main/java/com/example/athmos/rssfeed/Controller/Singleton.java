package com.example.athmos.rssfeed.Controller;

import com.example.athmos.rssfeed.Model.User;

/**
 * Created by athmos on 26/01/17.
 */
public class                Singleton {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User            user;



    private static          Singleton ourInstance = new Singleton();
    public static           Singleton getInstance() {
        return ourInstance;
    }

    private                 Singleton() {
    }
}
