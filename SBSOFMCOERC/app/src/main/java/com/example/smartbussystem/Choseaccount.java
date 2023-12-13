package com.example.smartbussystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class Choseaccount extends AppCompatActivity {
    RadioButton studentlogin,driverlogin,adlogin;
    private RadioButton Adminlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choseaccount);
        studentlogin=findViewById(R.id.studentlogin);
        driverlogin=findViewById(R.id.driverlogin);
        Adminlogin=findViewById(R.id.Adminlogin);
      //  adlogin=findViewById(R.id.adlogin);

        studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Loginpage.class));
            }
        });

        driverlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DriverLogin.class));
            }
        });
        Adminlogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ADlogin.class));
            }
        }));


    }
}