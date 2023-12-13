package com.example.smartbussystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartbussystem.ui.DriverRequestFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class DriverInterface extends AppCompatActivity  {
    private CardView mtotalstudent;
    ImageView tscanner,tlogout;

    EditText issue;
    Button msubmit;
    //Toolbar ToolBardriverinterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_interface);
        issue=findViewById(R.id.issue);
        msubmit=findViewById(R.id.submit);
       // mtotalstudent=(CardView) findViewById(R.id.totalstudent);
        tscanner=findViewById(R.id.tscanner);
        tlogout=findViewById(R.id.tlogout);
      //  ToolBardriverinterface =findViewById(R.id.ToolBardriverinterface);

       /* mtotalstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TotalStudentDriver.class));
            }
        });

        */
        tscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DriverInterface.this, "Logingout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DriverLogin.class));
                onBackPressed();

            }
        });

        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putString("mText", issue.getText().toString());
                updateLocation();
            }
        });

    }

    public void updateLocation()
    {
        String BusNumber="";
        DriverLogin currentdriver=new DriverLogin();
       // if(mdriverUsername.getText().toString().equalsIgnoreCase("Wadjeprakash@mcorcbus.gmail.com"))
        {
            BusNumber=currentdriver.CurrentDriverdetailwithLocation.busid;
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // mDatabase.child("DriverLocation/"+user.getUid()+"/DriverID").setValue(""+user.getEmail());
        mDatabase.child("driver/"+BusNumber+"/driver_Problem").setValue(issue.getText().toString());
        // mDatabase.child("DriverLocation/DriverID/Latlong").setValue(""+latitude+","+longitude);

    }
    public void scanCode()
    {
        ScanOptions options=new ScanOptions();
        options.setPrompt("volume up to flash on");
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher barLaucher=registerForActivityResult(new ScanContract(),result ->
    {
       if(result.getContents()!=null)
       {
           AlertDialog.Builder builder =new AlertDialog.Builder(DriverInterface.this);
           builder.setTitle("Result");
           builder.setMessage(result.getContents());
           builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
               }
           }).show();
       }

    });
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.drivermenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.dscanner){

            scanCode();
        }
        else if(itemId==R.id.dlogout)
        {
            Toast.makeText(DriverInterface.this, "Logingout", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Loginpage.class));
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
 */


}