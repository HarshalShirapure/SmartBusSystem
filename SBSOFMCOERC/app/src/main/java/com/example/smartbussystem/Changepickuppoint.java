package com.example.smartbussystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Changepickuppoint extends AppCompatActivity {
    EditText moldpoint,mnewpoint;
    Button mchangepoint;
    Toolbar Toolbarpickuppoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepickuppoint);
        moldpoint=findViewById(R.id.oldpoint);
        mnewpoint=findViewById(R.id.newpoint);
        mchangepoint=findViewById(R.id.changepoint);
        //Toolbarpickuppoint =findViewById(R.id.Toolbarpickuppoint);

       /* setSupportActionBar(Toolbarpickuppoint);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Change pickup point");
        }
        */


        mchangepoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Changepickuppoint.this, "Pickup point changed", Toast.LENGTH_SHORT).show();
            }

        });
    }
}