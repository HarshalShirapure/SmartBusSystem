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

public class Bus5Details extends AppCompatActivity {
    Toolbar ToolbarBus5;
    ListView bus5list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus5_details);
    /*    ToolbarBus5=findViewById(R.id.ToolbarBus5);
        setSupportActionBar(ToolbarBus5);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus5 Details");

     */

        bus5list=findViewById(R.id.bus5list);
        ArrayList<String> aL5=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(Bus5Details.this,R.layout.allbusesdetailsadmin,aL5);
        bus5list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1440").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL5.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    //{
                    busdetls bd5=snapshot.getValue(busdetls.class);
                    String u5 = "name: " + bd5.getDname();
                    String v5="mobile: " + bd5.getDno();
                    String w5="way: " + bd5.getRoot();
                    aL5.add(u5);
                    aL5.add(v5);
                    aL5.add(w5);
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