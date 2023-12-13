package com.example.smartbussystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Registerpage extends AppCompatActivity {
    EditText Fullname,Mobileno1,Mobileno2,Email,pw,repw;
    Button selectimg,Upload,register,alreadyaccount;
    ImageView backarrow;
    AutoCompleteTextView yearbranch,pickup_point,total_instalment;
    public String BusNumber;

    //firebase database
    public static final String TAG="TAG";
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    // for Image storing
    private Uri uri;
    private  final int IMG_REQUEST_ID=0;

    //for adding item in spinner
    ArrayList<String> YearBranch=new ArrayList<>();
    ArrayList<String> Pickuppoint=new ArrayList<>();
    //ArrayList<String> Fee=new ArrayList<>();
    ArrayList<String> Studentroot=new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter2;
    private TextView Bus_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        Fullname  = findViewById(R.id.Fullname);
        Mobileno1 = findViewById(R.id.Mobileno1);
        Mobileno2 = findViewById(R.id.Mobileno2);
        Email = findViewById(R.id.Email);
        pw = findViewById(R.id.pw);
        repw = findViewById(R.id.repw);
        selectimg = findViewById(R.id.selectimg);
        Upload = findViewById(R.id.Upload);
        register = findViewById(R.id.register);
        alreadyaccount = findViewById(R.id.alreadyaccount);
        backarrow = findViewById(R.id.backarrow);
        yearbranch = findViewById(R.id.yearbranch);
        pickup_point = findViewById(R.id.pickup_point);
        //feespinner=findViewById(R.id.feespinner);
        total_instalment = findViewById(R.id.total_installment);
        Bus_Number=findViewById(R.id.Bus_Number);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //Selecting image from The gallery.
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select image"), IMG_REQUEST_ID);
            }
        });
        //Uploading image to firebase storage.
        Upload.setEnabled(false);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(Registerpage.this);
                pd.setTitle("Uploding..");
                pd.show();

                if (uri != null) {
                    StorageReference ref = storageReference.child("student_id/" + UUID.randomUUID().toString());
                    ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(Registerpage.this, "uploded!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registerpage.this, "error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                selectimg.setEnabled(true);
                Upload.setEnabled(false);
            }

        });

        //alreadyaccount button
        alreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Loginpage.class));
            }
        });

        //backarrow button
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Loginpage.class));
                onBackPressed();
            }
        });


        //yearbranch

        YearBranch.add("select year branch");

        YearBranch.add("FE,IT");
        YearBranch.add("FE,COMPUTER");
        YearBranch.add("FE,ELECTRICAL");
        YearBranch.add("FE,MECHANICAL");
        YearBranch.add("FE,CIVIL");
        YearBranch.add("FE,E&TC");

        YearBranch.add("SE,IT");
        YearBranch.add("SE,COMPUTER");
        YearBranch.add("SE,ELECTRICAL");
        YearBranch.add("SE,MECHANICAL");
        YearBranch.add("SE,CIVIL");
        YearBranch.add("SE,E&TC");

        YearBranch.add("TE,IT");
        YearBranch.add("TE,COMPUTER");
        YearBranch.add("TE,ELECTRICAL");
        YearBranch.add("TE,MECHANICAL");
        YearBranch.add("TE,CIVIL");
        YearBranch.add("TE,E&TC");

        YearBranch.add("BE,IT");
        YearBranch.add("BE,COMPUTER");
        YearBranch.add("BE,ELECTRICAL");
        YearBranch.add("BE,MECHANICAL");
        YearBranch.add("BE,CIVIL");
        YearBranch.add("BE,E&TC");


        ArrayAdapter<String> spinnerAdapter1=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,YearBranch);
        yearbranch.setAdapter(spinnerAdapter1);
        yearbranch.setThreshold(2);

        //pickuppoint
        Pickuppoint.add("Jehancircle, Fees:22000, Bus No: MH15AK883");
        Pickuppoint.add("Ashokstambh, Fees:20000, Bus No: MH15AK883");
        Pickuppoint.add("nimani, Fees:20000, Bus No: MH15AK883");
        Pickuppoint.add("aurangabad naka, Fees:18000, Bus No: MH15AK883");
        Pickuppoint.add("AshokNagar , Fees:22000 , Bus No: MH15AK1440");
        Pickuppoint.add("Trimurti Chouk, Fees:20000,  Bus No: MH15AK1440");
        Pickuppoint.add("Gadkari chouk, Fees:20000,  Bus No: MH15AK1440");
        Pickuppoint.add("Dwarka, Fees:22000, Bus No: MH15AK1440");
        Pickuppoint.add("Bhagur: Fees:22000,  Bus No: MH15AK1442");
        Pickuppoint.add("devlaligao, Fees:18000,  Bus No: MH15AK1442");
        Pickuppoint.add("Bytco, Fees:20000,  Bus No: MH15AK1442");
        Pickuppoint.add("sinner,Fees:18000,  Bus No: MH15AK1443");
        Pickuppoint.add("shinde,Fees:18000,  Bus No: MH15AK1443");
        Pickuppoint.add("palse,Fees:18000,  Bus No: MH15AK1443");
        Pickuppoint.add("Bytco,Fees:18000,  Bus No: MH15AK1443");
        Pickuppoint.add("vadangali,Fees:18000,  Bus No: MH15AK1233");
        Pickuppoint.add("Hiwargao,Fees:18000,  Bus No: MH15AK1233");
        Pickuppoint.add("Bhendali,Fees:18000,  Bus No: MH15AK1233");
        Pickuppoint.add("saikheda,Fees:18000,  Bus No: MH15AK1233");
        Pickuppoint.add("vinchur,Fees:18000,  Bus No: MH15AK741");
        Pickuppoint.add("niphad,Fees:18000,  Bus No: MH15AK741");
        Pickuppoint.add("chandori,Fees:18000,  Bus No: MH15AK741");
        Pickuppoint.add("kokangao,Fees:18000,  Bus No: MH15AK1235");
        Pickuppoint.add("ozar,Fees:18000,  Bus No: MH15AK1235");
        Pickuppoint.add("jatrahotel,Fees:18000,  Bus No: MH15AK1235");
        Pickuppoint.add("nandurnaka,Fees:18000,  Bus No: MH15AK1235");
        Pickuppoint.add("mhasrul,Fees:18000,  Bus No: MH15AK1253");
        Pickuppoint.add("rasbihari international school,Fees:18000,  Bus No: MH15AK1253");
        Pickuppoint.add("nilgiri baugh,Fees:18000,  Bus No: MH15AK1253");
        Pickuppoint.add("Dwarka,Fees:18000,  Bus No: MH15AK712");
        Pickuppoint.add("upnagar,Fees:18000,  Bus No: MH15AK712");
        Pickuppoint.add("Bytko,Fees:18000,  Bus No: MH15AK712");
        Pickuppoint.add("Jailroad,Fees:18000,  Bus No: MH15AK712");
        Pickuppoint.add("Bytko,Fees:18000,  Bus No: MH15AK1559");
        Pickuppoint.add("dattamandir,Fees:18000,  Bus No: MH15AK1559");
        Pickuppoint.add("kathe lane,Fees:18000,  Bus No: MH15AK1559");
        Pickuppoint.add("tapowan,Fees:18000,  Bus No: MH15AK1559");
        Pickuppoint.add("shivaji nagar,Fees:18000,  Bus No: MH15AK1236");
        Pickuppoint.add("Bytco,Fees:18000,  Bus No: MH15AK1236");
        Pickuppoint.add("Dattamandir,Fees:18000,  Bus No: MH15AK1236");
        Pickuppoint.add("dwarka,Fees:18000,  Bus No: MH15AK1236");
        Pickuppoint.add("aurangabad naka,Fees:18000,  Bus No: MH15AK1236");

         spinnerAdapter2=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Pickuppoint);
        pickup_point.setAdapter(spinnerAdapter2);
        pickup_point.setThreshold(2);

        //installment
        Studentroot.add("Vadangali-Hiwargoan-Bhendali-Saikheda-Matoshri College");
        Studentroot.add("Kokangaon-Ozar-Jatra Hotel-Nandur Naka-Matoshri College");
        Studentroot.add("Shivaji Nagar-Bytco-Dattamandir-Dwarka-Aurangabad Naka-Matoshri College");
        Studentroot.add("Mhasrul-Rasbihari International School-Nilgiri Baugh-Matoshri College");
        Studentroot.add("Ashok Nagar-Trimurti Chouk-Gadkari Chouk-Dwarka-Matoshri College");
        Studentroot.add("Bhagur-Devolaligoan-Bytco-Jailroad-Matoshri College");
        Studentroot.add("Sinnar-Shinde-Palse-Bytco-Matoshri College");
        Studentroot.add("Pathardi Phata-Mumbai Naka-Kathe Galli-Tapovan-Matoshri College");
        Studentroot.add("Bytco-Dattamandir-Kathe Lane-Tapowan-Matoshri College");
        Studentroot.add("Dwarka-Upnagar-Bytco-Jailraod-Matoshri College");
        Studentroot.add("Vinchur-Niphad-Chandori-Matoshri College");
        Studentroot.add("Jehan Circle-Ashok Stambh-Nimani-Aurangabad Naka-Matoshri College");

        ArrayAdapter<String> spinnerAdapter3=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Studentroot);
        total_instalment.setAdapter(spinnerAdapter3);
        total_instalment.setThreshold(2);

        pickup_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bus_Number.setText("Bus Number For Selected Pickup : "+getBusNumber());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname=Fullname.getText().toString();
                String mobileno1=Mobileno1.getText().toString();
                String mobileno2=Mobileno2.getText().toString();
                final String email=Email.getText().toString().trim();
                String Pw=pw.getText().toString();
                String rePw=repw.getText().toString();
                String yb=yearbranch.getText().toString();
                String picpoint=pickup_point.getText().toString();
                String instal=total_instalment.getText().toString();
                if(TextUtils.isEmpty(fullname))
                {
                    Fullname.setError("Field is required!");
                    return;
                }
                if(TextUtils.isEmpty(mobileno1))
                {
                    Mobileno1.setError("Field is empty");
                    return;
                }
                else if(Mobileno1.length()<10)
                {
                    Mobileno1.setError("mobile no must be equal to 10 digits!");
                    return;
                }
                if(TextUtils.isEmpty(mobileno2))
                {
                    Mobileno2.setError("Field is empty");
                    return;
                }
                else if(Mobileno2.length()<10)
                {
                    Mobileno2.setError("mobile no must be equal to 10 digits!");
                    return;
                }

                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Field is required");
                    return;
                }
                if(TextUtils.isEmpty(Pw))
                {
                    pw.setError("Field is required!");
                    return;
                }
                else if(pw.length()<8)
                {
                    pw.setError("must be greater then 8 digits!");
                    return;
                }
                if(TextUtils.isEmpty(rePw))
                {
                    repw.setError("Field is required!");
                    return;
                }
                else if(repw.length()<8)
                {
                    repw.setError("must be greater then 8 digits!");
                    return;
                }

              /*
               else if(pw)
                {
                    repw.setError("password not matched!");
                    return;
                }
               */
                else
                {
                    startActivity(new Intent(getApplicationContext(),Loginpage.class));
                }


                Pickuppoint.add("Jehancircle, Fees:22000, Bus No: MH15AK883");
                Pickuppoint.add("Ashokstambh, Fees:20000, Bus No: MH15AK883");
                Pickuppoint.add("nimani, Fees:20000, Bus No: MH15AK883");
                Pickuppoint.add("aurangabad naka, Fees:18000, Bus No: MH15AK883");
                Pickuppoint.add("AshokNagar , Fees:22000 , Bus No: MH15AK1440");
                Pickuppoint.add("Trimurti Chouk, Fees:20000,  Bus No: MH15AK1440");
                Pickuppoint.add("Gadkari chouk, Fees:20000,  Bus No: MH15AK1440");
                Pickuppoint.add("Dwarka, Fees:22000, Bus No: MH15AK1440");
                Pickuppoint.add("Bhagur: Fees:22000,  Bus No: MH15AK1442");
                Pickuppoint.add("devlaligao, Fees:18000,  Bus No: MH15AK1442");
                Pickuppoint.add("Bytco, Fees:20000,  Bus No: MH15AK1442");
                Pickuppoint.add("sinner,Fees:18000,  Bus No: MH15AK1443");
                Pickuppoint.add("shinde,Fees:18000,  Bus No: MH15AK1443");
                Pickuppoint.add("palse,Fees:18000,  Bus No: MH15AK1443");
                Pickuppoint.add("Bytco,Fees:18000,  Bus No: MH15AK1443");
                Pickuppoint.add("vadangali,Fees:18000,  Bus No: MH15AK1233");
                Pickuppoint.add("Hiwargao,Fees:18000,  Bus No: MH15AK1233");
                Pickuppoint.add("Bhendali,Fees:18000,  Bus No: MH15AK1233");
                Pickuppoint.add("saikheda,Fees:18000,  Bus No: MH15AK1233");
                Pickuppoint.add("vinchur,Fees:18000,  Bus No: MH15AK741");
                Pickuppoint.add("niphad,Fees:18000,  Bus No: MH15AK741");
                Pickuppoint.add("chandori,Fees:18000,  Bus No: MH15AK741");
                Pickuppoint.add("kokangao,Fees:18000,  Bus No: MH15AK1235");
                Pickuppoint.add("ozar,Fees:18000,  Bus No: MH15AK1235");
                Pickuppoint.add("jatrahotel,Fees:18000,  Bus No: MH15AK1235");
                Pickuppoint.add("nandurnaka,Fees:18000,  Bus No: MH15AK1235");
                Pickuppoint.add("mhasrul,Fees:18000,  Bus No: MH15AK1253");
                Pickuppoint.add("rasbihari international school,Fees:18000,  Bus No: MH15AK1253");
                Pickuppoint.add("nilgiri baugh,Fees:18000,  Bus No: MH15AK1253");
                Pickuppoint.add("Dwarka,Fees:18000,  Bus No: MH15AK712");
                Pickuppoint.add("upnagar,Fees:18000,  Bus No: MH15AK712");
                Pickuppoint.add("Bytko,Fees:18000,  Bus No: MH15AK712");
                Pickuppoint.add("Jailroad,Fees:18000,  Bus No: MH15AK712");
                Pickuppoint.add("Bytko,Fees:18000,  Bus No: MH15AK1559");
                Pickuppoint.add("dattamandir,Fees:18000,  Bus No: MH15AK1559");
                Pickuppoint.add("kathe lane,Fees:18000,  Bus No: MH15AK1559");
                Pickuppoint.add("tapowan,Fees:18000,  Bus No: MH15AK1559");
                Pickuppoint.add("shivaji nagar,Fees:18000,  Bus No: MH15AK1236");
                Pickuppoint.add("Bytco,Fees:18000,  Bus No: MH15AK1236");
                Pickuppoint.add("Dattamandir,Fees:18000,  Bus No: MH15AK1236");
                Pickuppoint.add("dwarka,Fees:18000,  Bus No: MH15AK1236");
                Pickuppoint.add("aurangabad naka,Fees:18000,  Bus No: MH15AK1236");



                String Total_Fees="0";

                if(picpoint.contains("18000"))
                {
                    Total_Fees="18000";

                }else if(picpoint.contains("20000"))
                {
                    Total_Fees="20000";

                }
                else if(picpoint.contains("22000"))
                {
                    Total_Fees="22000";

                }


                     //   ||picpoint.equalsIgnoreCase("")||picpoint.equalsIgnoreCase("")||picpoint.equalsIgnoreCase("")||picpoint.equalsIgnoreCase("")||picpoint.equalsIgnoreCase("")||picpoint.equalsIgnoreCase(""))

                BusNumber=getBusNumber();
                onBackPressed();
                rootNode =FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Students");
                UserHelper userHelper=new UserHelper(fullname,mobileno1,mobileno2,email,Pw,rePw,yb,picpoint,instal,Total_Fees,BusNumber);
                reference.child(fullname).setValue(userHelper);
                storage=FirebaseStorage.getInstance();
                storageReference=storage.getReference();

                fAuth.createUserWithEmailAndPassword(email,Pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser fuser=fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Registerpage.this, "Register Succesful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onfailure:Email Not sent " + e.getMessage());
                                }
                            });



                            Toast.makeText(Registerpage.this, "User Created", Toast.LENGTH_SHORT).show();
                            String UserId=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("user").document(UserId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fname",fullname);
                            user.put("Email",email);
                            user.put("Mobile_No1",Mobileno1);
                            user.put("Mobile_No2",Mobileno2);
                            user.put("Year and Branch",YearBranch);
                            user.put("pickup point",pickup_point);
                            user.put("BusNumber",BusNumber);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onsuccess: user profile is created for "+ UserId);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onfailure: " +e.toString());
                                }
                            });

                        }

                    }

                });
        /*     userHelper1.setYearbranch(yearbranch.getSelectedItem().toString());
              userHelper1.setPickup_point(pickup_point.getSelectedItem().toString());
              userHelper1.setTotal_instalment(total_instalment.getSelectedItem().toString());

              reference.child(String.valueOf(maxid+1)).setValue(userHelper1);
        */

            }

        });
/*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxid = (int) snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
 */
    }

    public String getBusNumber()
    {
        String BusNumber="";

        //for(int i=0;i<pickup_point.length();i++)

           // String tempPickuppoint=pickup_point.get(i);
           String SelectedPickup= pickup_point.getText().toString();

        if(SelectedPickup.contains("MH15AK883"))
        {
            return BusNumber="MH15AK883";
        }
            else if (SelectedPickup.contains("MH15AK741"))
    {
        return BusNumber="MH15AK741";
    }
    else if (SelectedPickup.contains("MH15AK712"))
    {
        return BusNumber="MH15AK712";
    }
    else if (SelectedPickup.contains("MH15AK1547"))
    {
        return BusNumber="MH15AK1547";
    }
    else if (SelectedPickup.contains("MH15AK1443"))
    {
        return BusNumber="MH15AK1443";
    }
    else if (SelectedPickup.contains("MH15AK1442"))
    {
        return BusNumber="MH15AK1442";
    }
    else if (SelectedPickup.contains("MH15AK1440"))
    {
        return BusNumber="MH15AK1440";
    }
    else if (SelectedPickup.contains("MH15AK1253"))
    {
        return BusNumber="MH15AK1253";
    }
    else if (SelectedPickup.contains("MH15AK1236"))
    {
        return BusNumber="MH15AK1236";
    }
    else if (SelectedPickup.contains("MH15AK1235"))
    {
        return BusNumber="MH15AK1235";
    }
    else if (SelectedPickup.contains("MH15AK1233"))
    {
        return BusNumber="MH15AK1233";
    }
    return BusNumber;
    }
//storing image to firabase storage.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode==IMG_REQUEST_ID)&& resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uri=data.getData();
            try{
                Bitmap bit=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                Upload.setEnabled(true);
                selectimg.setEnabled(false);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}