
package com.example.smartbussystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AdminAllBuses extends AppCompatActivity {
    Toolbar Toolbarallbuses;
    ImageView tb;
//    Button mclick;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_buses);

       /* Toolbarallbuses = findViewById(R.id.Toolbarallbuses);
        setSupportActionBar(Toolbarallbuses);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("All Buses");

        */
        tb=findViewById(R.id.tb);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BottomNavigation.class));
            }
        });
        list=findViewById(R.id.list);
  //      mclick=findViewById(R.id.click);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    startActivity(new Intent(getApplicationContext(),Bus1Details.class));
                }
                else if(position==1)
                {
                    startActivity(new Intent(getApplicationContext(),Bus2Details.class));
                }
                else if(position==2)
                {
                    startActivity(new Intent(getApplicationContext(),Bus3Details.class));
                }

                else if(position==3)
                {
                    startActivity(new Intent(getApplicationContext(),Bus4Details.class));
                }

                else if(position==4)
                {
                    startActivity(new Intent(getApplicationContext(),Bus5Details.class));
                }


                else if(position==5)
                {
                    startActivity(new Intent(getApplicationContext(),Bus6Details.class));
                }


                else if(position==6)
                {
                    startActivity(new Intent(getApplicationContext(),Bus7Details.class));
                }


                else if(position==7)
                {
                    startActivity(new Intent(getApplicationContext(),Bus8Details.class));
                }


                else if(position==8)
                {
                    startActivity(new Intent(getApplicationContext(),Bus9Details.class));
                }


                else if(position==9)
                {
                    startActivity(new Intent(getApplicationContext(),Bus10Details.class));
                }


                else if(position==10)
                {
                    startActivity(new Intent(getApplicationContext(),Bus11Details.class));
                }


                else if(position==11)
                {
                    startActivity(new Intent(getApplicationContext(),Bus12Details.class));
                }

            }
        });

        ArrayList<String> a=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(AdminAllBuses.this,R.layout.allbusesdetailsadmin,a);
        list.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("Bus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    a.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        Busesadmindetails t=snapshot1.getValue(Busesadmindetails.class);
                        String y=t.getTakebus();
                        a.add(y);
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