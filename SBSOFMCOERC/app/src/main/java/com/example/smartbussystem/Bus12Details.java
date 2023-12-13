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

public class Bus12Details extends AppCompatActivity {

    Toolbar ToolbarBus12;
    ListView bus12list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus12_details);

     /*   ToolbarBus12=findViewById(R.id.ToolbarBus12);
        bus12list=findViewById(R.id.bus12list);

        setSupportActionBar(ToolbarBus12);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus12 Details");

      */


        bus12list=findViewById(R.id.bus12list);
        ArrayList<String> aL12=new ArrayList<>();
        ArrayAdapter adapter12=new ArrayAdapter<String>(Bus12Details.this,R.layout.allbusesdetailsadmin,aL12);
        bus12list.setAdapter(adapter12);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root712").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL12.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    // {
                    busdetls bd12=snapshot.getValue(busdetls.class);
                    String u12 = "name: " + bd12.getDname();
                    String v12="mobile: " + bd12.getDno();
                    String w12="way: " + bd12.getRoot();
                    aL12.add(u12);
                    aL12.add(v12);
                    aL12.add(w12);
                    //}
                    adapter12.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}