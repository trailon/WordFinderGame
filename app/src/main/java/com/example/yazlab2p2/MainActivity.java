package com.example.yazlab2p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {
    public static int yenioyun;
            public EditText isimgir;
            public static String oyuncuismi;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                isimgir = findViewById(R.id.isim);
                Button mYeniOyun = findViewById(R.id.buttonnewgame);
                Button mDevamEt = findViewById(R.id.buttoncontinue);
                Button mYuksekSkorlar = findViewById(R.id.buttonHighScores);
                mYeniOyun.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        yenioyun = 1;
                        oyuncuismi = isimgir.getText().toString();
                        StartNewGame();
                    }
                });
                mDevamEt.setOnClickListener(new View.OnClickListener() {
                    @Override
            public void onClick(View v) {
                oyuncuismi = isimgir.getText().toString();
                yenioyun = 0;
                Continue();
            }
        });
        mYuksekSkorlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScores();
            }
        });
    }
    private void HighScores(){
        Intent intent = new Intent(this,high_scores.class);
        startActivity(intent);
    }
    private void Continue() {
        Intent intent = new Intent(this,GameScreen.class);
        startActivity(intent);
    }
    public void StartNewGame() {
        Intent intent = new Intent(this,GameScreen.class);
        startActivity(intent);
    }
}
