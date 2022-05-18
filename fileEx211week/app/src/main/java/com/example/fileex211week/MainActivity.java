package com.example.fileex211week;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button btnPrev, btnNext;
    myPictureView myPicture;
    int curNum=1;
    File[] imageFiles;
    String imageFname;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 이미지 뷰어");
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        textView = (TextView) findViewById(R.id.textview1);
        myPicture = (myPictureView) findViewById(R.id.myPictureView1);

        imageFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures").listFiles();
        imageFname = imageFiles[0].toString();
        myPicture.imagePath = imageFname;

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curNum<=1){
                    curNum=imageFiles.length;
                    imageFname=imageFiles[curNum-1].toString();
                    myPicture.imagePath=imageFname;
                    myPicture.invalidate();
                    textView.setText(String.valueOf(curNum)+"/"+String.valueOf(imageFiles.length));
//                    Toast.makeText(getApplicationContext(), "첫번재 긂입니다 ", Toast.LENGTH_SHORT).show();
                } else{
                    curNum--;
                    imageFname = imageFiles[curNum-1].toString();
                    myPicture.imagePath=imageFname;
                    myPicture.invalidate();
                    textView.setText(String.valueOf(curNum)+"/"+String.valueOf(imageFiles.length));
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curNum >= imageFiles.length-1){
                    curNum=1;
                    imageFname=imageFiles[curNum-1].toString();
                    myPicture.imagePath=imageFname;
                    myPicture.invalidate();
                    textView.setText(String.valueOf(curNum)+"/"+String.valueOf(imageFiles.length));
//                    Toast.makeText(getApplicationContext(), "마지막 그림입니다", Toast.LENGTH_SHORT).show();
                } else{
                    curNum++;
                    imageFname=imageFiles[curNum-1].toString();
                    myPicture.imagePath=imageFname;
                    myPicture.invalidate();
                }
                textView.setText(String.valueOf(curNum)+"/"+String.valueOf(imageFiles.length));
            }
        });
    }
}