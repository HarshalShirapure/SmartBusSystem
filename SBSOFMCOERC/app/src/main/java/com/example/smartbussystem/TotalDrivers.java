package com.example.smartbussystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TotalDrivers extends AppCompatActivity {
    Toolbar ToolbarDrivers;
    ListView listView;
    ImageView td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_drivers);
        // ToolbarDrivers=findViewById(R.id. ToolbarDrivers);
       /* setSupportActionBar( ToolbarDrivers);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setTitle("Total Drivers");

        */

        td=findViewById(R.id.td);
        td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BottomNavigation.class));
            }
        });
        listView=findViewById(R.id.driverlist);


        ArrayList<String> b=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(TotalDrivers.this,R.layout.allbusesdetailsadmin,b);
        listView.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("bus_root").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists())
                {
                    b.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        busdetls d=snapshot1.getValue(busdetls.class);
                        String w="Driver name: " + d.getDname() +","
                                + " \n Driver Contact: " + d.getDno();//+ ","
                        //+"\n Route: " + d.getRoot();

                        b.add(w);

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}