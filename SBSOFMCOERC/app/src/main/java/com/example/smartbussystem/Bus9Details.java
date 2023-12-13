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

public class Bus9Details extends AppCompatActivity {

    Toolbar ToolbarBus9;
    ListView bus9list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus9_details);

       /* ToolbarBus9=findViewById(R.id.ToolbarBus9);
        bus9list=findViewById(R.id.bus9list);

        setSupportActionBar(ToolbarBus9);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus9 Details");

        */

        bus9list=findViewById(R.id.bus9list);
        ArrayList<String> aL9=new ArrayList<>();
        ArrayAdapter adapter9=new ArrayAdapter<String>(Bus9Details.this,R.layout.allbusesdetailsadmin,aL9);
        bus9list.setAdapter(adapter9);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root741").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL9.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    // {
                    busdetls bd9=snapshot.getValue(busdetls.class);
                    String u9 = "name: " + bd9.getDname();
                    String v9="mobile: " + bd9.getDno();
                    String w9="way: " + bd9.getRoot();
                    aL9.add(u9);
                    aL9.add(v9);
                    aL9.add(w9);
                    //}
                    adapter9.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}