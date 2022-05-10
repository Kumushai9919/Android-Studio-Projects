package com.example.tinpetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class ProfileActivity extends AppCompatActivity {
    String Appid = "application-0-erjbs";
    String userId = "";
    EditText nameEdt;
    Button saveBtn;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveBtn = (Button)findViewById(R.id.saveBtn);
        nameEdt = (EditText) findViewById(R.id.nameEdt);

        Realm.init(getApplicationContext()); //1.Realm database init

        App app = new App(new AppConfiguration.Builder(Appid).build());

        Credentials credentials = Credentials.emailPassword("kumush@gmail.com", "123456");
        app.loginAsync(credentials, new App.Callback<User>() {      //3. we're initializing the app and login
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess())
                {
                    Log.v("User", "Logged in successfully");
                    User user = app.currentUser();
                    mongoClient = user.getMongoClient("mongodb-atlas"); //we use mongodb Atlas service
                    mongoDatabase = mongoClient.getDatabase("petdatas"); //our databaseName
                    mongoCollection = mongoDatabase.getCollection("users");
                    userId = user.getId().toString();

                }
                else
                {
                    Log.v("User", "Failed" + result.getError().toString());
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Document document = new Document().append("userId", userId).append("username", nameEdt.getText().toString());
                mongoCollection.insertOne(document).getAsync(result->{
                    if(result.isSuccess())
                    {
                        Log.v("Data", "Data inserted successfully");
                     }
                    else
                    {
                        Log.v("Data", "Error: " + result.getError().toString());
                    }
                });


            }
        });


    }
}