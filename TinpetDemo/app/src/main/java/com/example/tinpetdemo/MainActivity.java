package com.example.tinpetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.AlgorithmParameterGenerator;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {
    String Appid = "application-0-erjbs";
    Button registerBtn;
    EditText emailedt, passwordedt, newaccedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = (Button) findViewById(R.id.loginBtn1);
        emailedt = (EditText) findViewById(R.id.emailedt);
        passwordedt = (EditText) findViewById(R.id.passwordedt);
        newaccedt = (EditText) findViewById(R.id.newaccedt);

        Realm.init(getApplicationContext()); //1.Realm database init

        App app = new App(new AppConfiguration.Builder(Appid).build()); //2.create Realm instance with Realm Appid

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Credentials credentials = Credentials.emailPassword(emailedt.getText().toString(), passwordedt.getText().toString());
                app.loginAsync(credentials, new App.Callback<User>() {      //3. we're initializing the app and login
                    @Override
                    public void onResult(App.Result<User> result) {
                        if(result.isSuccess())
                        {
                            Log.v("User", "Logged in successfully");
                            Toast.makeText(MainActivity.this, "Loged successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Log.v("User", "Failed" + result.getError().toString());
                            Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        newaccedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });



//        app.getEmailPassword().registerUserAsync(emailedt.getText().toString(), passwordedt.getText().toString(), it->{
//            if(it.isSuccess())
//            {
//                Log.v("User", "Register with email successfully");
//
//            }
//            else
//            {
//                Log.v("User", "Register Failed");
//
//            }
//        });



    }
}