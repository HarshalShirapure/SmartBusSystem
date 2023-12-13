package com.example.smartbussystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbussystem.ui.Busdetails.BusdetailFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import android.content.Context;

import java.util.List;
import java.util.Map;

public class Loginpage extends AppCompatActivity {

    EditText musername, mpassword;
    Button mloginBtn;
    TextView mCreateBtn, mforgetPassword;
    ProgressBar progressBar1;

    FirebaseAuth fAuth;

    String Uid;
    private DatabaseReference databaseReference_Student;
    private DatabaseReference databaseReference_Driver;
    public static UserHelper LoggedinUser= new UserHelper();
    public static busdetls DriverDetails= new busdetls();
    public static driver CurrentDriverdetailwithLocation= new driver();
    public static Map<String , UserHelper> CurrentUserMapValueTemp ;
    private static DatabaseReference databaseReference_Drivertable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        musername = findViewById(R.id.Username);
        mpassword = findViewById(R.id.password1);
        mloginBtn = findViewById(R.id.LoginBtn1);
        mCreateBtn = findViewById(R.id.createtext1);
        mforgetPassword = findViewById(R.id.forgetPassword);

        databaseReference_Student= FirebaseDatabase.getInstance().getReference("Students");
        databaseReference_Driver= FirebaseDatabase.getInstance().getReference("bus_root");
        databaseReference_Drivertable= FirebaseDatabase.getInstance().getReference("driver");
       // databaseReference_Stude= FirebaseDatabase.getInstance().getReference("Students");
        fAuth = FirebaseAuth.getInstance();

// See the UserRecord reference doc for the contents of userRecord.
      //  System.out.println("Successfully fetched user data: " + userRecord.getUid());
       // UserHelper userRecord = fAuth.getInstance().get
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registerpage.class));
            }
        });

        mforgetPassword.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(Loginpage.this, "Reset Link is Send to your Mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Loginpage.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        DatabaseReference databaseReference;


        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = musername.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (TextUtils.isEmpty(Username)) {
                    musername.setError("UserName is Required!");
                    return;
                }


                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password is Required!");
                    return;
                }


                if (password.length() < 8) {
                    mpassword.setError("Password must be greater then 8 character!");
                    return;
                }

//                progressBar1.setVisibility(View.VISIBLE);

               // startActivity(new Intent(getApplicationContext(), Main2Activity.class));
/*
                fAuth.signInWithEmailAndPassword(Username, password).addOnCompleteListener(Loginpage.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                          //  progressBar.setVisibility(View.GONE);
                        }
                    }
                });

 */


                fAuth.signInWithEmailAndPassword(Username,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = fAuth.getCurrentUser();
                       String Temp_= authResult.getAdditionalUserInfo().toString();
                       String temp_username= user.getDisplayName();
                        String temp_phone= user.getPhoneNumber();
                        String temp_fees= user.getUid();

                        startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                        SharedPreferences sharedpreferences = getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("UserID",Username);
                        editor.putString("Password",password);
                        editor.commit();
                        GetUserdetails();
                      //  onBackPressed();
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
    public void GetUserdetails()
    {
        databaseReference_Student.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // UserHelper businfo=snapshot.getValue(UserHelper.class);
                    //String picp="pickup: "+businfo.getPickup_point();


                    // temp.setEmail("z@gmail.com");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    Object tempp = snapshot.getChildren();
                    Map<String, List<UserHelper>> value = (Map<String, List<UserHelper>>) snapshot.getValue();

                    UserHelper uh = snapshot.getValue(UserHelper.class);
                    SharedPreferences SP= getApplicationContext().getSharedPreferences("LoginUser",Context.MODE_PRIVATE);

                    for(Map.Entry<String,List<UserHelper>> entry :value.entrySet())
                    {

                        Map<String , UserHelper> TempValue = (Map<String, UserHelper>) entry.getValue();
                        Object currentemail = TempValue.get("email");
                        String Temp2=entry.getKey();


                        if(currentemail.toString().equalsIgnoreCase(SP.getString("UserID","")))
                        {
                            UserHelper TempObj=new UserHelper();

                            Object email = TempValue.get("email");
                            Object fullname = TempValue.get("fullname");
                            Object mobileno1 = TempValue.get("mobileno1");
                            Object mobileno2 = TempValue.get("mobileno2");
                            Object pickup_point = TempValue.get("pickup_point");
                            Object pw = TempValue.get("pw");
                            Object repw = TempValue.get("repw");
                            Object total_Fees = TempValue.get("total_Fees");
                            Object total_instalment = TempValue.get("total_instalment");
                            Object yearbranch = TempValue.get("yearbranch");
                            Object BusNumber = TempValue.get("busNumber");
                            TempObj.setEmail(email.toString());
                            TempObj.setFullname(fullname.toString());
                            TempObj.setMobileno1(mobileno1.toString());
                            TempObj.setMobileno2(mobileno2.toString());
                            TempObj.setPickup_point(pickup_point.toString());
                            TempObj.setPw(pw.toString());
                            TempObj.setRepw(repw.toString());
                            TempObj.setTotal_Fees(total_Fees.toString());
                            TempObj.setTotal_instalment(total_instalment.toString());
                            TempObj.setYearbranch(yearbranch.toString());
                            TempObj.setBusNumber(BusNumber.toString());

                            LoggedinUser=TempObj;
                            CurrentUserMapValueTemp=TempValue;
                           // DriverDetails.setText(pickup_point.toString());
                        }
                    }
                    GetDriverdetails();

                    //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//----------------------------------------------------------------------------------------------------------------



//                        for(int i=0;i<value.size();i++)
//                        {
//                            UserHelper current_obj = value.get(value.keySet());
//                            if(current_obj.getEmail()=="z@gmail.com")
//                            {
//                                LoggedinUser=current_obj;
//
//                            }
//
//                        }



             /*   arrayList.clear();
                String startpicup=snapshot.child(uid).child("pickup_point").getValue(String.class);

                arrayList.add(startpicup);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.fragment_busdetail,arrayList);
                bdfrag.setAdapter(adapter);

              */
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String err = error.toString();
            }
        });

    }

    public void GetDriverdetails()
    {
        databaseReference_Driver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // UserHelper businfo=snapshot.getValue(UserHelper.class);
                    //String picp="pickup: "+businfo.getPickup_point();


                    // temp.setEmail("z@gmail.com");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    Object tempp = snapshot.getChildren();
                    Map<String, List<busdetls>> value = (Map<String, List<busdetls>>) snapshot.getValue();

                    UserHelper uh = snapshot.getValue(UserHelper.class);
                    SharedPreferences SP= getApplicationContext().getSharedPreferences("LoginUser",Context.MODE_PRIVATE);

                    for(Map.Entry<String,List<busdetls>> entry :value.entrySet())
                    {

                        Map<String , busdetls> TempValue = (Map<String, busdetls>) entry.getValue();
                        Object currentroot = LoggedinUser.getTotal_instalment();
                        String Temp2=entry.getKey();


                        if(currentroot.toString().contains(""+(Object)TempValue.get("root")))
                        {
                            busdetls TempObj=new busdetls();

                            Object dname = TempValue.get("dname");
                            Object dno = TempValue.get("dno");
                            Object root = TempValue.get("root");
                            TempObj.setDname(dname.toString());
                            TempObj.setDno(dno.toString());
                            TempObj.setRoot(root.toString());


                            DriverDetails =TempObj;

                            // DriverDetails.setText(pickup_point.toString());
                        }
                    }

                    GetDriverdetailsTable();


                    //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//----------------------------------------------------------------------------------------------------------------



//                        for(int i=0;i<value.size();i++)
//                        {
//                            UserHelper current_obj = value.get(value.keySet());
//                            if(current_obj.getEmail()=="z@gmail.com")
//                            {
//                                LoggedinUser=current_obj;
//
//                            }
//
//                        }



             /*   arrayList.clear();
                String startpicup=snapshot.child(uid).child("pickup_point").getValue(String.class);

                arrayList.add(startpicup);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.fragment_busdetail,arrayList);
                bdfrag.setAdapter(adapter);

              */

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String err = error.toString();
            }
        });

    }

    public static void GetDriverdetailsTable()
    {
        databaseReference_Drivertable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // UserHelper businfo=snapshot.getValue(UserHelper.class);
                    //String picp="pickup: "+businfo.getPickup_point();


                    // temp.setEmail("z@gmail.com");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    Object tempp = snapshot.getChildren();
                    Map<String, List<driver>> value = (Map<String, List<driver>>) snapshot.getValue();

                    UserHelper uh = snapshot.getValue(UserHelper.class);
                   // SharedPreferences SP= getApplicationContext().getSharedPreferences("LoginUser",Context.MODE_PRIVATE);

                    for(Map.Entry<String,List<driver>> entry :value.entrySet())
                    {

                        Map<String , driver> TempValue = (Map<String, driver>) entry.getValue();
                        Object currentroot = LoggedinUser.getPickup_point();
                        String Temp2=entry.getKey();


                        if(currentroot.toString().contains(""+Temp2))
                        {
                            driver TempObj=new driver();
                            Object email=TempValue.get("EmailID");
                            Object Location_Current=TempValue.get("Location_Current");
                            Object busid=TempValue.get("busid");
                            Object name_=TempValue.get("name");

                            TempObj.setEmailID(email.toString());
                            TempObj.setLocation_Current(Location_Current.toString());
                            TempObj.setBusid(busid.toString());
                            TempObj.setName(name_.toString());

//                            Object dname = TempValue.get("dname");
//                            Object dno = TempValue.get("dno");
//                            Object root = TempValue.get("root");
//                            TempObj.setDname(dname.toString());
//                            TempObj.setDno(dno.toString());
//                            TempObj.setRoot(root.toString());


                            CurrentDriverdetailwithLocation =TempObj;

                            // DriverDetails.setText(pickup_point.toString());
                        }
                    }




                    //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//----------------------------------------------------------------------------------------------------------------



//                        for(int i=0;i<value.size();i++)
//                        {
//                            UserHelper current_obj = value.get(value.keySet());
//                            if(current_obj.getEmail()=="z@gmail.com")
//                            {
//                                LoggedinUser=current_obj;
//
//                            }
//
//                        }



             /*   arrayList.clear();
                String startpicup=snapshot.child(uid).child("pickup_point").getValue(String.class);

                arrayList.add(startpicup);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.fragment_busdetail,arrayList);
                bdfrag.setAdapter(adapter);

              */
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String err = error.toString();
            }
        });

    }

    public void getDriverLocation()
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DriverLocation");
       // databaseReference.getDatabase().
       // mDatabase.child("DriverLocation/").

              //  CollectionReference cities = mDatabase.Coll
// Create a query against the collection.
       // Query query = cities.whereEqualTo("capital", true);
       // mDatabase.child("DriverLocation/"+user.getUid()+"/Latlong").setValue(""+latitude+","+longitude);
        // mDatabase.child("DriverLocation/DriverID/Latlong").setValue(""+latitude+","+longitude);


    }
}