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

public class studentAdmindashboard extends AppCompatActivity {
    Toolbar ToolbarStudent;
    ListView adminstdlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_admindashboard);
       // ToolbarStudent=findViewById(R.id.ToolbarStudent);
//        setSupportActionBar(ToolbarStudent);
//        if(getSupportActionBar()!=null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        getSupportActionBar().setTitle("Total Students In each bus");
        /*
        adminstdlist=findViewById(R.id.adminstdlist);
        ArrayList<String> student=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(studentAdmindashboard.this,R.layout.activity_student_admindashboard,student);
        adminstdlist.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("Students").child("").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    student.clear();
                 //   for(DataSnapshot snapshot1:snapshot.getChildren())
                   // {
                    UserHelper userHelper = snapshot.getValue(UserHelper.class);
                    String w = userHelper.getFullname();
                    String v = userHelper.getBusNumber();
                    String x = userHelper.getTotal_Fees();
                    student.add(w);
                    student.add(v);
                    student.add(x);
                    //}
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         */

        adminstdlist=findViewById(R.id.adminstdlist);
        ArrayList<String> a=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(studentAdmindashboard.this,R.layout.allbusesdetailsadmin,a);
        adminstdlist.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference().child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    a.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        UserHelper u=snapshot1.getValue(UserHelper.class);
                        String y=u.getFullname();
                        //+ " " +u.getTotal_Fees() + " " + u.getMobileno1() + " " +u.getEmail() + " " + " " + u.getEmail() + " " +u.getTotal_instalment() + " " + u.getPw() + " " + u.getYearbranch() + " " +u.getMobileno2();
                         String v=u.getTotal_Fees();
                         a.add(y);
                        a.add(v);
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