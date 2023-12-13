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

public class Bus10Details extends AppCompatActivity {

    Toolbar ToolbarBus10;
    ListView bus10list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus10_details);

      /*  ToolbarBus10=findViewById(R.id.ToolbarBus10);
        bus10list=findViewById(R.id.bus10list);

        setSupportActionBar(ToolbarBus10);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus10 Details");

       */
        bus10list=findViewById(R.id.bus10list);
        ArrayList<String> aL10=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(Bus10Details.this,R.layout.allbusesdetailsadmin,aL10);
        bus10list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1235").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL10.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    // {
                    busdetls bd10=snapshot.getValue(busdetls.class);
                    String u10 = "name: " + bd10.getDname();
                    String v10="mobile: " + bd10.getDno();
                    String w10="way: " + bd10.getRoot();
                    aL10.add(u10);
                    aL10.add(v10);
                    aL10.add(w10);
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