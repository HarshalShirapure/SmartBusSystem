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

public class Bus11Details extends AppCompatActivity {

    Toolbar ToolbarBus11;
    ListView bus11list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus11_details);
/*
        ToolbarBus11=findViewById(R.id.ToolbarBus11);
        bus11list=findViewById(R.id.bus11list);

        setSupportActionBar(ToolbarBus11);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus11 Details");

 */

        bus11list=findViewById(R.id.bus11list);
        ArrayList<String> aL11=new ArrayList<>();
        ArrayAdapter adapter11=new ArrayAdapter<String>(Bus11Details.this,R.layout.allbusesdetailsadmin,aL11);
        bus11list.setAdapter(adapter11);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1253").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL11.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    // {
                    busdetls bd11=snapshot.getValue(busdetls.class);
                    String u11 = "name: " + bd11.getDname();
                    String v11="mobile: " + bd11.getDno();
                    String w11="way: " + bd11.getRoot();
                    aL11.add(u11);
                    aL11.add(v11);
                    aL11.add(w11);
                    //}
                    adapter11.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}