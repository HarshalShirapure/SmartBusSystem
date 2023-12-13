package com.example.smartbussystem.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.Busfee;
import com.example.smartbussystem.R;

public class HomeFragment extends Fragment {

    Activity context;
    Button btn1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context=getActivity();

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        view.findViewById(R.id.permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });

        return view;

    }

    public void onStart()
    {
        super.onStart();
        Button btn=(Button) context.findViewById(R.id.Feebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Busfee.class);
                startActivity(intent);
            }
        });
    }
 }
