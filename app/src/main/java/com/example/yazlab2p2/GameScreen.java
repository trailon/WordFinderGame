package com.example.yazlab2p2;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameScreen extends AppCompatActivity {
    private boolean running;
    private long pauseOffset;
    Handler handler;
    public TextView Kelime;
    public TextView Seviye;
    public TextView Attempts;
    public Button harf1;
    public Button harf2;
    public Button harf3;
    public Button harf4;
    public Button harf5;
    public Button shuffle;
    public Button ara;
    public Button sil;
    public Chronometer chronometer;
    public String stage;
    public String[] data = new String[18];
    public String[] skortablosu = new String[]{"1-1\n","1-2\n","1-3\n","1-4\n","1-5\n","1-6\n","2-1\n","2-2\n","2-3\n","2-4\n","2-5\n","2-6\n","3-1\n","3-2\n","3-3\n","3-4\n","3-5\n","3-6"};
    public static int solvecontrol;
    public TextView[][] PuzzleViews = new TextView[9][9];
    public String[] kelimeler;
    public List<String> harflist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        harf1 = findViewById(R.id.harf1);
        harf2 = findViewById(R.id.harf2);
        harf3 = findViewById(R.id.harf3);
        harf4 = findViewById(R.id.harf4);
        harf5 = findViewById(R.id.harf5);
        shuffle = findViewById(R.id.Shuffle);
        Kelime = findViewById(R.id.Kelime);
        Attempts = findViewById(R.id.Attempts);
        chronometer = findViewById(R.id.zaman);
        ara = findViewById(R.id.ara);
        sil = findViewById(R.id.harfsil);
        Seviye = findViewById(R.id.Seviye);
        handler = new Handler();
        chronometer.setFormat("Time: %s");
        String line;
        try {
            FileInputStream fileIn=openFileInput("kelimeler.txt");
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

        if(MainActivity.yenioyun == 1){
            startChronometer();
            solvecontrol = 0;
            stage = "1-1";
            Seviye.setText(stage);
            KelimeOku(stage);
        }else if(MainActivity.yenioyun == 2){
            startChronometer();
            solvecontrol = 0;
            Seviye.setText(stage);
            KelimeOku(stage);
        }else{
            startChronometer();
            try {
                FileInputStream fileIn=openFileInput("checkpoint.txt");
                InputStreamReader InputRead= new InputStreamReader(fileIn);
                BufferedReader bin = new BufferedReader(InputRead);
                stage = bin.readLine();
                InputRead.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            KelimeOku(stage);
            Seviye.setText(stage);
            solvecontrol = 0;

        }
        harf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kelime_update(harf1);
            }
        });
        harf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kelime_update(harf2);
            }
        });
        harf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kelime_update(harf3);
            }
        });
        harf4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kelime_update(harf4);
            }
        });
        harf5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kelime_update(harf5);
            }
        });
        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Kelime.length()>0) Kelime.setText(Kelime.getText().subSequence(0,Kelime.length()-1));
            }
        });
        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bul(Kelime.getText().toString());
            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shuffle();
            }
        });
    }
    public void startChronometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset=0;
    }
    private void Bul(String kelimebul) {
        int attemptkontrol = 1 ;
        for(int i = 0 ; i < kelimeler.length ; i++){
            if(kelimeler[i].equals(kelimebul)){
                for(int j = 2 ; j < kelimeler[i].length()+2 ; j++){
                    PuzzleViews[i+1][j].setBackgroundColor(Color.parseColor("#FFFFFF"));

                }
                Kelime.setText("");
                solvecontrol++;
            }else{
                attemptkontrol = 0;
            }
        }
        if(attemptkontrol==0){
            int tempint = Integer.parseInt(Attempts.getText().toString());
            tempint++;
            Attempts.setText(String.valueOf(tempint));
        }
        if(solvecontrol == kelimeler.length){
            solvecontrol = 0;
            Attempts.setText("0");
            int temp1 = Integer.parseInt(String.valueOf(stage.charAt(0))),temp2 = Integer.parseInt(String.valueOf(stage.charAt(2)));
            if(temp2<6)
                temp2++;
            else {
                temp1++;
                temp2 = 1;
            }
            stage=temp1+"-"+temp2;
            try {
                FileOutputStream fileout=openFileOutput("checkpoint.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write(stage);
                outputWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int dk=Integer.parseInt(chronometer.getText().toString().substring(6,8));
            int sure = dk*60+Integer.parseInt(chronometer.getText().toString().substring(9));
            int failedattempt =Integer.parseInt(Attempts.getText().toString())-kelimeler.length;
            if (failedattempt == 0)
                failedattempt = 1;
            int puan = 10000/(sure*failedattempt);
            puan = Math.abs(puan);
            SkorYaz(temp1,temp2,puan);
            resetChronometer();
            Seviye.setText(stage);
            KelimeOku(stage);
            Shuffle();
            Kelime.setText("");
        }
    }
    private void SkorYaz(int stagepart1,int stagepart2,int bolumpuani){
        try {
            FileOutputStream fileout=openFileOutput("skorlar.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            if(skortablosu[(stagepart1-1)*6+stagepart2-1].length()<5)
            skortablosu[(stagepart1-1)*6+stagepart2-1]=stagepart1+"-"+stagepart2+" "+MainActivity.oyuncuismi+" "+ bolumpuani+"\n";
            else{
                skortablosu[(stagepart1-1)*6+stagepart2-1]=skortablosu[(stagepart1-1)*6+stagepart2-1].substring((4+MainActivity.oyuncuismi.length()),(4+MainActivity.oyuncuismi.length()+String.valueOf(bolumpuani).length()-1));
            }
            for (String s : skortablosu) {
                outputWriter.write(s);
            }
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Puzzle() {

        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                PuzzleViews[i][j] = new TextView(this);
                String textviewid = "a"+(i+1)+(j+1);
                int resID = getResources().getIdentifier(textviewid,"id",getPackageName());
                PuzzleViews[i][j] = findViewById(resID);
            }
        }
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                PuzzleViews[i][j].setText("X");
                PuzzleViews[i][j].setBackgroundColor(Color.TRANSPARENT);
            }}
            for(int i = 0 ; i < kelimeler.length ; i++){          // Kelimelerde gezmek için
                for(int j = 2 ; j < kelimeler[i].length()+2 ; j++){       // Kelimeyi harf harf yazdırmak için
                    PuzzleViews[i+1][j].setText(String.valueOf(kelimeler[i].charAt(j-2)));
                    PuzzleViews[i+1][j].setBackgroundColor(Color.parseColor("#000000"));
                }
            }

    }

    private void KelimeOku(String level) {
        int seviye = Integer.parseInt(level.substring(0,1))-1,altseviye = Integer.parseInt(level.substring(2));
        int kelimeuzunlugu = seviye+3;
        harflist.clear();
        kelimeler = new String[altseviye];
        Random rand = new Random();
        int sayi = rand.nextInt((3 - 1) + 1) + 1,temp=0;
        for(int x = 5 ; x < data[seviye*6+altseviye-1].length() ; x++){
        if(data[seviye*6+altseviye-1].substring(x,x+1).equals(String.valueOf(sayi)))
            temp = x+1;
        }
        String tempkelime;
        for(int j = 0 ; j < altseviye ; j++){
        tempkelime = data[seviye*6+altseviye-1].substring(temp,temp+kelimeuzunlugu);
        kelimeler[j] = tempkelime.toUpperCase();
        temp=temp+kelimeuzunlugu;
        }
        for (String s : kelimeler) {
            for (int j = 0; j < s.length(); j++) {
                if (!harflist.contains(String.valueOf(s.charAt(j)).toUpperCase()))
                    harflist.add(String.valueOf(s.charAt(j)).toUpperCase());
            }
        }
        Puzzle();
        Shuffle();
    }

    private void Shuffle() {
        Collections.shuffle(harflist);
        for(int v = 0 ; v < harflist.size() ; v++){
            switch (v){
                case 0:
                    harf1.setText(harflist.get(v));
                    break;
                case 1:
                    harf2.setText(harflist.get(v));
                    break;
                case 2:
                    harf3.setText(harflist.get(v));
                    break;
                case 3:
                    harf4.setText(harflist.get(v));
                    break;
                case 4:
                    harf5.setText(harflist.get(v));
                    break;
            }

        }
    }

    public void Kelime_update(Button button){
        if(Kelime.length()<5)
            Kelime.append(button.getText());
    }
}
