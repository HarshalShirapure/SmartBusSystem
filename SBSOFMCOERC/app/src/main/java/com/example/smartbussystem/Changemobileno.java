package com.example.smartbussystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartbussystem.databinding.ActivityChangemobilenoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Changemobileno extends AppCompatActivity {
    EditText moldno,mnewno;
    Button mchangeno;
    Toolbar Toolbarmobileno;

    ActivityChangemobilenoBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        moldno=findViewById(R.id.oldno);
        mnewno=findViewById(R.id.newno);
        mchangeno=findViewById(R.id.changeno);
//        Toolbarmobileno=findViewById(R.id.Toolbarmobileno);

        binding=ActivityChangemobilenoBinding.inflate(getLayoutInflater());

/*
        setSupportActionBar(Toolbarmobileno);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Change Mobile Number");
        }

 */
        mchangeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldno=binding.oldno.getText().toString();
                String newno=binding.newno.getText().toString();

                updatemobno(oldno,newno);

                if(TextUtils.isEmpty(oldno))
                {
                    moldno.setError("Enter old username");
                    return;
                }
                if(oldno.length()<10)
                {
                    moldno.setError("Enter number is less then 10 digits");
                    return;
                }

                if(TextUtils.isEmpty(newno))
                {
                    moldno.setError("Enter new username");
                    return;
                }
                if(newno.length()<10)
                {
                    mnewno.setError("Enter number is less then 10 digits");
                    return;
                }
                Toast.makeText(Changemobileno.this, "mobile no Changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updatemobno(String oldno, String newno) {
        HashMap mobileno=new HashMap();
        mobileno.put("oldno",oldno);
        mobileno.put("newno",newno);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Students");
        databaseReference.child(oldno).updateChildren(mobileno).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    binding.oldno.setText("");
                    binding.newno.setText("");
                    Toast.makeText(Changemobileno.this, "updated succesfully!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Changemobileno.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}