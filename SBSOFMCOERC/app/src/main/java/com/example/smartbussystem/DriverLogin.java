package com.example.smartbussystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
import androidx.core.app.ActivityCompat;

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

import java.util.List;
import java.util.Map;

public class DriverLogin extends AppCompatActivity {


    EditText mdriverUsername, mdriverpassword;
    Button mdriverloginbtn;
    TextView mdriverforgetPassword;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;

    LocationManager locationManager;
    String latitude, longitude;
    public static FirebaseUser user;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10000;
    private Query databaseReference_Drivertable;
    public static driver CurrentDriverdetailwithLocation;
    public static String BusNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        mdriverUsername = findViewById(R.id.driverUsername);
        mdriverpassword = findViewById(R.id.driverpassword);
        mdriverloginbtn = findViewById(R.id.driverloginbtn);
        mdriverforgetPassword = findViewById(R.id.driverforgetPassword);

        fAuth = FirebaseAuth.getInstance();

        databaseReference_Drivertable= FirebaseDatabase.getInstance().getReference("driver");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
          //  getLocation();
        }

        mdriverforgetPassword.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(DriverLogin.this, "Reset Link is Send to your Mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DriverLogin.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        mdriverloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dUsername = mdriverUsername.getText().toString().trim();
                String dpassword = mdriverpassword.getText().toString().trim();

                if (TextUtils.isEmpty(dUsername)) {
                    mdriverUsername.setError("UserName is Required!");
                    return;
                }


                if (TextUtils.isEmpty(dpassword)) {
                    mdriverpassword.setError("Password is Required!");
                    return;
                }


                if (dpassword.length() < 7) {
                    mdriverpassword.setError("Password must be equal to 12 character!");
                    return;
                }


                //        progressBar2.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(dUsername, dpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();
                         user = fAuth.getCurrentUser();
                        GetDriverdetailsTable();
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            OnGPS();
                        } else {
                            getLocation();
                        }

                     //  String email= user.ide;
                        startActivity(new Intent(getApplicationContext(), DriverInterface.class));

                       // onBackPressed();
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

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                DriverLogin.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                DriverLogin.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            try {


                Location locationGPS = getLastKnownLocation();
                //locationManager.getLastKnownLocation(LocationManager.KEY_LOCATIONS);
                if (locationGPS != null) {
                    double lat = locationGPS.getLatitude();
                    double longi = locationGPS.getLongitude();
                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(longi);
                    updateLocation();

                    handler.postDelayed(runnable = new Runnable() {
                        public void run() {
                            handler.postDelayed(runnable, delay);
                            updateLocation();
                           // Toast.makeText(MainActivity.this, "This method is run every 10 seconds",
                              //      Toast.LENGTH_SHORT).show();
                        }
                    }, delay);

                /*showLocation.setText("Your Location: " + "
                        " + "Latitude: " + latitude + "
                " + "Longitude: " + longitude);*/
                } else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                String error = e.getMessage().toString();
            }
          //  GetDriverdetailsTable();
        }
    }

    public Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
               // return TODO;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
    
    public void updateLocation()
    {
         BusNumber="";
        if(mdriverUsername.getText().toString().equalsIgnoreCase(CurrentDriverdetailwithLocation.EmailID.toString()))
        {
            BusNumber=CurrentDriverdetailwithLocation.busid;
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
       // mDatabase.child("DriverLocation/"+user.getUid()+"/DriverID").setValue(""+user.getEmail());
        mDatabase.child("driver/"+BusNumber+"/Location_Current").setValue(""+latitude+","+longitude);
       // mDatabase.child("DriverLocation/DriverID/Latlong").setValue(""+latitude+","+longitude);
       // GetDriverdetailsTable();
    }

    public  void GetDriverdetailsTable()
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
                        Object currentroot = mdriverUsername.getText().toString();
                        String Temp2 = ""+TempValue.get("EmailID");


                        if(currentroot.toString().equalsIgnoreCase(""+Temp2))
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
                            break;
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
    /* private void getLocation () {

        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
           // textview = (TextView)findViewById(R.id.fieldLatitude);
            //textview.setText(stringLatitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            //textview = (TextView)findViewById(R.id.fieldLongitude);
            //textview.setText(stringLongitude);

            String country = gpsTracker.getCountryName(this);
            //textview = (TextView)findViewById(R.id.fieldCountry);
            //textview.setText(country);

            String city = gpsTracker.getLocality(this);
            //textview = (TextView)findViewById(R.id.fieldCity);
            //textview.setText(city);

            String postalCode = gpsTracker.getPostalCode(this);
            //textview = (TextView)findViewById(R.id.fieldPostalCode);
            //textview.setText(postalCode);

            String addressLine = gpsTracker.getAddressLine(this);
            //textview = (TextView)findViewById(R.id.fieldAddressLine);
            //textview.setText(addressLine);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }


        *//*if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                String latitude = String.valueOf(lat);
                String longitude = String.valueOf(longi);
                // showLocation.setText("Your Location: " + " " + "Latitude: " + latitude + " " + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }*//*
    }*/

}