package com.example.smartbussystem;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bus7Details extends AppCompatActivity {
 Toolbar ToolbarBus7;
 ListView bus7list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus7_details);

/*
        ToolbarBus7=findViewById(R.id.ToolbarBus7);
        setSupportActionBar(ToolbarBus7);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus7 Details");

 */

        bus7list=findViewById(R.id.bus7list);
        ArrayList<String> aL7=new ArrayList<>();
        ArrayAdapter adapter7=new ArrayAdapter<String>(Bus7Details.this,R.layout.allbusesdetailsadmin,aL7);
        bus7list.setAdapter(adapter7);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1443").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL7.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    // {
                    busdetls bd7=snapshot.getValue(busdetls.class);
                    String u7 = "name: " + bd7.getDname();
                    String v7="mobile: " + bd7.getDno();
                    String w7="way: " + bd7.getRoot();
                    aL7.add(u7);
                    aL7.add(v7);
                    aL7.add(w7);
                    //}
                    adapter7.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}