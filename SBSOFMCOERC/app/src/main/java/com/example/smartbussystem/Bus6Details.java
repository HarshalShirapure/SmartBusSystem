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

public class Bus6Details extends AppCompatActivity {

    Toolbar ToolbarBus6;
    ListView bus6list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus6_details);

       /* ToolbarBus6=findViewById(R.id.ToolbarBus6);
        setSupportActionBar(ToolbarBus6);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus6 Details");

        */

        bus6list=findViewById(R.id.bus6list);
        ArrayList<String> aL6=new ArrayList<>();
        ArrayAdapter adapter6=new ArrayAdapter<String>(Bus6Details.this,R.layout.allbusesdetailsadmin,aL6);
        bus6list.setAdapter(adapter6);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1442").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL6.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                   // {
                    busdetls bd6=snapshot.getValue(busdetls.class);
                    String u6 = "name: " + bd6.getDname();
                    String v6="mobile: " + bd6.getDno();
                    String w6="way: " + bd6.getRoot();
                    aL6.add(u6);
                    aL6.add(v6);
                    aL6.add(w6);
                    //}
                    adapter6.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }
}