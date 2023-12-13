package com.example.smartbussystem.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.AdminAllBuses;
import com.example.smartbussystem.R;
import com.example.smartbussystem.TotalDrivers;
import com.example.smartbussystem.studentAdmindashboard;

public class HomeFragmentAdmin extends Fragment {
    CardView mtotalbuses,mDrivers,mStudents;
    Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        View view= inflater.inflate(R.layout.fragment_homeadmindashbord, container, false);
        mtotalbuses=view.findViewById(R.id.totalbuses);
        mtotalbuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AdminAllBuses.class));
            }
        });

        mDrivers=view.findViewById(R.id.Drivers);
        mDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TotalDrivers.class));
            }
        });

        mStudents=view.findViewById(R.id.Students);
        mStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, studentAdmindashboard.class));
            }
        });



        return view;
    }
}