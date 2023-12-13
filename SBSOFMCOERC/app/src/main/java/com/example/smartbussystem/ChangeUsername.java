package com.example.smartbussystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartbussystem.databinding.ActivityChangeUsernameBinding;
import com.google.firebase.database.DatabaseReference;


public class ChangeUsername extends AppCompatActivity {
    EditText moldname,mnewname;
    Button mchange;
    Toolbar Toolbarusername;
    ActivityChangeUsernameBinding binding;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangeUsernameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mchange=findViewById(R.id.change);
        moldname=findViewById(R.id.oldname);
        mnewname=findViewById(R.id.newname);
/*        Toolbarusername =findViewById(R.id.Toolbarusername);

        setSupportActionBar(Toolbarusername);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Change Username");
        }

 */
        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldusername=binding.oldname.getText().toString();
                String newusername=binding.newname.getText().toString();
//                updateUsername(oldusername);
            }
        });





    }

}