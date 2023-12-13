package com.example.smartbussystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Stnotification extends AppCompatActivity {

    Toolbar Notify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stnotification);
        //Notify=findViewById(R.id.Notify);
       /* setSupportActionBar(Notify);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Notification");
*/
    }
}