package com.example.downloadimageproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        //1 получить фотографию с базу данных
        //2 полученное фото вставим на аватарку imgView
        //3 если фото не получиться получить данные фото тогда просто устанавливаем default plus img
        //4 если фотография установлена но при нажатии на аватарку открыть голерею и при выборе фото изменить аватарку и сделать update
        // в монгодб
    }

    public void downloadImage(View view){
        if(isPermissionGranted()){
            pickImageFromGallery();
        } else{
            takePermission();
        }
    }
    private boolean isPermissionGranted(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            int readExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void takePermission(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category, DEFAULT");
                intent.setData(Uri.parse(String.format("package%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 100);
            } catch (Exception exception){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 100);
            }
        } else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
        }
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 100){
                if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                    if(Environment.isExternalStorageManager()){
                        pickImageFromGallery();
                    } else{
                        takePermission();
                    }
                }
            } else if(requestCode == 102){
                if(data != null){
                    Uri uri = data.getData();
                    if(uri != null){
                        imageView.setImageURI(uri);
                        Base64 base = b //do base and send to mongo, toast "Sent"
                    }
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0){
            if(requestCode == 101){
                boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if(readExternalStorage){
                    pickImageFromGallery();
                } else{
                    takePermission();
                }
            }
        }
    }
}






//
//public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
//
//
//    @Override
//    public Bitmap doInBackground(String... urls) {
//        try {
//            URL url = new URL(urls[0]);
//
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.connect();
//
//            InputStream in = connection.getInputStream();
//
//            Bitmap myBitmap = BitmapFactory.decodeStream(in);
//
//            return myBitmap;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//
//        }
//    }
//}