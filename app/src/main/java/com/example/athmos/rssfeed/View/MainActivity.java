package com.example.athmos.rssfeed.View;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.athmos.rssfeed.Controller.ApiManager;
import com.example.athmos.rssfeed.Controller.Singleton;
import com.example.athmos.rssfeed.Model.User;
import com.example.athmos.rssfeed.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button                  connexion;
    private Button                  inscription;
    private EditText                email;
    private EditText                password;
    public static String            MESSAGE;
    Singleton                       singleton;

    @Override
    protected void                  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initXml();
        singleton = Singleton.getInstance();
    }
    private void                    initXml() {
        connexion = (Button) findViewById(R.id.Connexion);
        connexion.setOnClickListener(onClicConnexion());
        inscription = (Button) findViewById(R.id.Insciption);
        inscription.setOnClickListener(onClicInscription());
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
    }

    private View.OnClickListener    onClicInscription() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiManager.getInstance(MainActivity.this).addUser(MainActivity.this, email.getText().toString(), password.getText().toString());
            }
        };
    }

    private View.OnClickListener    onClicConnexion() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiManager.getInstance(MainActivity.this).getUser(MainActivity.this, email.getText().toString(), password.getText().toString());
            }
        };
    }
    public void                    onResponseAddUser(boolean result)
    {
        if (!result) {
            System.out.println("ERROR CONNECTION");
            return;
        }
        Intent intent = new Intent(MainActivity.this, AppActivity.class);
        String message = email.getText().toString();
        MESSAGE = message;
        intent.putExtra(MESSAGE, MESSAGE);
        startActivity(intent);
    }

    public void onResponseGetUser(User user) {
        singleton.setUser(user);
        Intent intent = new Intent(MainActivity.this, AppActivity.class);
        String message = email.getText().toString();
        MESSAGE = message;
        intent.putExtra(MESSAGE, MESSAGE);
        startActivity(intent);
    }
}
