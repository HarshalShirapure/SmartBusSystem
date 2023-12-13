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

public class Bus3Details extends AppCompatActivity {
    Toolbar ToolbarBus3;
    ListView  bus3list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus3_details);
/*        ToolbarBus3=findViewById(R.id.ToolbarBus3);
        setSupportActionBar(ToolbarBus3);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus3 Details");

 */

        bus3list=findViewById(R.id.bus3list);


        ArrayList<String> aL3=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(Bus3Details.this,R.layout.allbusesdetailsadmin,aL3);
        bus3list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1236").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL3.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    //{
                    busdetls bd2=snapshot.getValue(busdetls.class);
                    String u = "name: " + bd2.getDname();
                    String v="mobile: " + bd2.getDno();
                    String w="way: " + bd2.getRoot();
                    aL3.add(u);
                    aL3.add(v);
                    aL3.add(w);
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