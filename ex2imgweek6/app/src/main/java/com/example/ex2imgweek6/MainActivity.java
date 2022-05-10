package com.example.ex2imgweek6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2;
    CheckBox chBox1;
    RadioGroup rdgroup1;
    RadioButton rdDog, rdCat, rdRabbit;
    Button btn1;
    ImageView imgPet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("애완동물 사진 보기");

        text1 = (TextView) findViewById(R.id.Text1);
        chBox1 = (CheckBox) findViewById(R.id.checkBox1);
        text2 = (TextView) findViewById(R.id.Text2);
        rdgroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        rdDog = (RadioButton) findViewById(R.id.radioDog);
        rdCat = (RadioButton) findViewById(R.id.radioCat);
        rdRabbit = (RadioButton) findViewById(R.id.radioRabbit);

        btn1 = (Button) findViewById(R.id.button1);
        imgPet = (ImageView) findViewById(R.id.imageView1);

        chBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chBox1.isChecked() == true) {
                    text2.setVisibility(View.VISIBLE);
                    rdgroup1.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.VISIBLE);
                    imgPet.setVisibility(View.VISIBLE);
                } else {
                    text2.setVisibility(View.INVISIBLE);
                    rdgroup1.setVisibility(View.INVISIBLE);
                    btn1.setVisibility(View.INVISIBLE);
                    imgPet.setVisibility(View.INVISIBLE);

                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rdgroup1.getCheckedRadioButtonId()) {
                    case R.id.radioDog:
                        imgPet.setImageResource(R.drawable.dog);
                        break;
                    case R.id.radioCat:
                        imgPet.setImageResource(R.drawable.cat1);
                        break;
                    case R.id.radioRabbit:
                        imgPet.setImageResource(R.drawable.rabbit);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "동물 먼저 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}