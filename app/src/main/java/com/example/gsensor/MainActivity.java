package com.example.gsensor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.gsensor.MapaSerwer.MapaSerwer;
import com.example.gsensor.Mappaczka.MapActivity;
import com.example.gsensor.Zbieranieinformacji.Zbieranieinfo;
import com.example.gsensor.help.HelpActivity;


public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActiwity";


    Button zbieranie;
    Button mapa;
    Button help;
    Button serwer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zbieranie = (Button)findViewById(R.id.zbieranie);
        zbieranie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent1 = new Intent(MainActivity.this, Zbieranieinfo.class);
                startActivity(myIntent1);
            }
        });

        mapa = (Button)findViewById(R.id.lokalnie);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent2 = new Intent(MainActivity.this, MapActivity.class);
                startActivity(myIntent2);

            }
        });


        serwer = (Button)findViewById(R.id.serwer);
        serwer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent3 = new Intent(MainActivity.this, MapaSerwer.class);
                startActivity(myIntent3);

            }
        });




        help = (Button)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent4 = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(myIntent4);

            }
        });




    }
}
