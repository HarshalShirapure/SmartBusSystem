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

public class Bus2Details extends AppCompatActivity {
    Toolbar ToolbarBus2;
    ListView bus2list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus2_details);
        /*ToolbarBus2=findViewById(R.id.ToolbarBus2);
        setSupportActionBar(ToolbarBus2);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus2 Details");

         */

        bus2list=findViewById(R.id.bus2list);


        ArrayList<String> aL2=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(Bus2Details.this,R.layout.allbusesdetailsadmin,aL2);
        bus2list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("bus_root").child("root1559").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    aL2.clear();
                    //for(DataSnapshot snapshot1:snapshot.getChildren())
                    //{
                        busdetls bd2=snapshot.getValue(busdetls.class);
                        String u2 = "name: " + bd2.getDname();
                        String v2="mobile: " + bd2.getDno();
                        String w2="way: " + bd2.getRoot();
                        aL2.add(u2);
                        aL2.add(v2);
                        aL2.add(w2);
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
