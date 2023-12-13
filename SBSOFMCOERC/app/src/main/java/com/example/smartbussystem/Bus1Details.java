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

public class Bus1Details extends AppCompatActivity {

    Toolbar ToolbarBus1;
    ListView bus1list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus1_details);
       /* ToolbarBus1=findViewById(R.id.ToolbarBus1);
        setSupportActionBar(ToolbarBus1);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Bus1 Details");

        */

        bus1list=findViewById(R.id.bus1list);


                ArrayList<String> arrayList=new ArrayList<>();
                ArrayAdapter adapter=new ArrayAdapter<String>(Bus1Details.this,R.layout.allbusesdetailsadmin,arrayList);
                bus1list.setAdapter(adapter);
                FirebaseDatabase.getInstance().getReference("bus_root").child("root883").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            arrayList.clear();
                            //for(DataSnapshot snapshot1:snapshot.getChildren())
                            //{
                                busdetls bd=snapshot.getValue(busdetls.class);
                                    String u1 = "name: " + bd.getDname();
                                    String v1="mobile: " + bd.getDno();
                                    String w1="way: " + bd.getRoot();
                                    arrayList.add(u1);
                                    arrayList.add(v1);
                                    arrayList.add(w1);
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