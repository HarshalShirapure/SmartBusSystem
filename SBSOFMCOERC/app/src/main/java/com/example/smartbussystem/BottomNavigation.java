package com.example.smartbussystem;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartbussystem.ui.DriverRequestFragment;
import com.example.smartbussystem.ui.HomeFragmentAdmin;
import com.example.smartbussystem.ui.NotificationFragment;
import com.example.smartbussystem.ui.StudentRequestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigation extends AppCompatActivity {

    BottomNavigationView mbtmnavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        mbtmnavigation=findViewById(R.id.btmnavigation);
        mbtmnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.adminHome){
                   loadFrag(new HomeFragmentAdmin(),false);
                } //else if(id==R.id.notification){
                    //loadFrag(new NotificationFragment(),false);

 //               }
                else if(id==R.id.StudentRequest){
                    loadFrag(new StudentRequestFragment(),false);
                }else{
                    loadFrag(new DriverRequestFragment(),false);
                }
                return true;
            }
        });

       mbtmnavigation.setSelectedItemId(R.id.adminHome);
    }

    public void loadFrag(Fragment fragement,boolean flag)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag)
            ft.add(R.id.container, fragement);

        else
            ft.replace(R.id.container,fragement);
      //  ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(mbtmnavigation.getSelectedItemId()==R.id.adminHome)
        {
            super.onBackPressed();
        }
        else
        {
            mbtmnavigation.setSelectedItemId(R.id.adminHome);
        }
    }
}