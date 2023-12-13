package com.example.smartbussystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ADlogin extends AppCompatActivity {
    EditText madUsername, madpassword;
    Button madloginbtn;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adlogin);

        madUsername=findViewById(R.id.adUsername);
        madpassword=findViewById(R.id.adpassword);
        madloginbtn=findViewById(R.id.adloginbtn);
        madloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Usernamead = madUsername.getText().toString().trim();
                String passwordad = madpassword.getText().toString().trim();

                if (TextUtils.isEmpty(Usernamead)) {
                    madUsername.setError("UserName is Required!");
                    return;
                }


                if (TextUtils.isEmpty(passwordad)) {
                    madpassword.setError("Password is Required!");
                    return;
                }

                startActivity(new Intent(getApplicationContext(),BottomNavigation.class));

            }
        });
    }
}