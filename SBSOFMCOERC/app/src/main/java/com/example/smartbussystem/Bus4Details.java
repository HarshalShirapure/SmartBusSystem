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

public class Bus4Details extends AppCompatActivity {
    Toolbar ToolbarBus4;
    ListView bus4list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus4_details);
       /* ToolbarBus4=findViewById(R.id.ToolbarBus4);
        setSupportActionBar(ToolbarBus4);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus4 Details");

        */


        bus4list=findViewById(R.id.bus4list);


        ArrayList<String> arrayList=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(Bus4Details.this,R.layout.allbusesdetailsadmin,arrayList);
        bus4list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1547").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    arrayList.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    //{
                    busdetls bd=snapshot.getValue(busdetls.class);
                    String u4 = "name: " + bd.getDname();
                    String v4="mobile: " + bd.getDno();
                    String w4="way: " + bd.getRoot();
                    arrayList.add(u4);
                    arrayList.add(v4);
                    arrayList.add(w4);
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