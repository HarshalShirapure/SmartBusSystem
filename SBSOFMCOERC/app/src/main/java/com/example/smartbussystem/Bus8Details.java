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

public class Bus8Details extends AppCompatActivity {

    Toolbar ToolbarBus8;
    ListView bus8list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus8_details);
/*
        ToolbarBus8=findViewById(R.id.ToolbarBus8);
        bus8list=findViewById(R.id.bus8list);

        setSupportActionBar(ToolbarBus8);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus8 Details");

 */

        bus8list=findViewById(R.id.bus8list);
        ArrayList<String> aL8=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(Bus8Details.this,R.layout.allbusesdetailsadmin,aL8);
        bus8list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1233").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL8.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren()
                    //{
                    busdetls bd5=snapshot.getValue(busdetls.class);
                    String u8 = "name: " + bd5.getDname();
                    String v8="mobile: " + bd5.getDno();
                    String w8="way: " + bd5.getRoot();
                    aL8.add(u8);
                    aL8.add(v8);
                    aL8.add(w8);
                    //}
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}