package com.example.smartbussystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Busstop extends AppCompatActivity {

    Toolbar ToolbarBuschange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstop);

     //   ToolbarBuschange=findViewById(R.id.ToolbarBuschange);
        /*setSupportActionBar(ToolbarBuschange);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bus Stop");
        }

         */


    }
}