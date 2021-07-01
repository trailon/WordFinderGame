package com.example.yazlab2p2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class high_scores extends AppCompatActivity {
    public TextView skorlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        skorlar = findViewById(R.id.skorlar);
        String[] data = new String[18];
        String line;
        try {
            FileInputStream fileIn=openFileInput("skorlar.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            BufferedReader in = new BufferedReader(InputRead);
            List<String> lines = new ArrayList<>();
            while((line = in.readLine()) != null) {
                lines.add(line);
            }
            data = lines.toArray(new String[]{});
            InputRead.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < 18 ; i++)
            skorlar.setText(skorlar.getText().toString().concat(data[i]+"\n"));

    }
}
