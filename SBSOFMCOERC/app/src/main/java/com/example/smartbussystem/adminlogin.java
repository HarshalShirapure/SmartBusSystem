package com.example.smartbussystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adminlogin extends AppCompatActivity {

    EditText madminUsername, madminpassword;
    Button madminloginbtn;
    TextView madminforgetPassword;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        madminUsername = findViewById(R.id.driverUsername);
        madminpassword = findViewById(R.id.driverpassword);
        madminloginbtn = findViewById(R.id.driverloginbtn);
        madminforgetPassword = findViewById(R.id.driverforgetPassword);

        fAuth = FirebaseAuth.getInstance();

        madminforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To received Reset Link: ");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(adminlogin.this, "Reset Link is Send to your Mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(adminlogin.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
            }

        });

        madminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dUsername = madminUsername.getText().toString().trim();
                String dpassword = madminpassword.getText().toString().trim();

                if (TextUtils.isEmpty(dUsername)) {
                    madminUsername.setError("UserName is Required!");
                    return;
                }


                if (TextUtils.isEmpty(dpassword)) {
                    madminpassword.setError("Password is Required!");
                    return;
                }


                if (dpassword.length() < 12) {
                    madminpassword.setError("Password must be equal to 12 character!");
                    return;
                }
                //        progressBar2.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(dUsername, dpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), DriverInterface.class));

                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }
}